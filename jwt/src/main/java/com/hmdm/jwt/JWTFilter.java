package com.hmdm.jwt;

import com.hmdm.persistence.UnsecureDAO;
import com.hmdm.persistence.domain.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final UnsecureDAO userDAO;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        log.debug("Starting JWTFilter for request URI: {}", request.getRequestURI());

        String jwt = resolveToken(request);

        if (jwt != null) {
            log.debug("JWT token found in request.");

            if (tokenProvider.validateToken(jwt)) {
                log.debug("JWT token is valid.");

                User authUser = tokenProvider.getAuthentication(jwt);
                log.debug("Extracted user from token: {}", authUser != null ? authUser.getLogin() : "null");

                if (authUser != null) {
                    User dbUser = userDAO.findByLoginOrEmail(authUser.getLogin());
                    log.debug("User fetched from DB: {}", dbUser != null ? dbUser.getLogin() : "null");

                    if (dbUser != null && dbUser.getAuthToken() != null &&
                            dbUser.getAuthToken().equals(authUser.getAuthToken())) {

                        log.debug("Auth token matches. Setting security context for user: {}", dbUser.getLogin());

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                dbUser, null, dbUser.getUserRole().getGrantedAuthorities()
                        );
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                    } else {
                        log.warn("Invalid JWT token or auth token mismatch for user: {}", authUser.getLogin());
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid JWT token");
                        return;
                    }
                }

            } else {
                log.warn("JWT token validation failed.");
            }

        } else {
            log.debug("No JWT token found in request.");
        }

        log.debug("Continuing filter chain for URI: {}", request.getRequestURI());
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        log.debug("Authorization header: {}", bearerToken);

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            log.debug("Extracted JWT token from header.");
            return token;
        }
        return null;
    }
}
