//package com.hmdm.jwt;
//
//import com.hmdm.persistence.UnsecureDAO;
//import com.hmdm.persistence.domain.User;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//public class JWTFilter extends OncePerRequestFilter {
//
//    private final TokenProvider tokenProvider;
//    private final UnsecureDAO userDAO;
//
//    public JWTFilter(TokenProvider tokenProvider, UnsecureDAO userDAO) {
//        this.tokenProvider = tokenProvider;
//        this.userDAO = userDAO;
//
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String jwt = resolveToken(request);
//        if (jwt != null && tokenProvider.validateToken(jwt)) {
//            User authUser = tokenProvider.getAuthentication(jwt);
//            User dbUser = userDAO.findByLoginOrEmail(authUser.getLogin());
//
//            if (dbUser != null && dbUser.getAuthToken() != null &&
//                    dbUser.getAuthToken().equals(authUser.getAuthToken())) {
//                System.out.println("userRole"+ dbUser.getUserRole());
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                        dbUser, null, dbUser.getUserRole().getGrantedAuthorities()
//                );
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            } else {
//                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid JWT token");
//                return;
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private String resolveToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            System.out.println(bearerToken);
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//}


// with logs
package com.hmdm.jwt;

import com.hmdm.persistence.UnsecureDAO;
import com.hmdm.persistence.domain.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);

    private final TokenProvider tokenProvider;
    private final UnsecureDAO userDAO;

    public JWTFilter(TokenProvider tokenProvider, UnsecureDAO userDAO) {
        this.tokenProvider = tokenProvider;
        this.userDAO = userDAO;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        logger.debug("Starting JWTFilter for request URI: {}", request.getRequestURI());

        String jwt = resolveToken(request);

        if (jwt != null) {
            logger.debug("JWT token found in request: {}", jwt);

            if (tokenProvider.validateToken(jwt)) {
                logger.debug("JWT token is valid.");

                User authUser = tokenProvider.getAuthentication(jwt);
                logger.debug("Extracted user from token: {}", authUser != null ? authUser.getLogin() : "null");
                System.out.println(authUser.getLogin());
                User dbUser = userDAO.findByLoginOrEmail(authUser.getLogin());
                logger.debug("User fetched from DB: {}", dbUser != null ? dbUser.getLogin() : "null");

                if (dbUser != null) {
                    logger.debug("DB User Role: {}", dbUser.getUserRole());
                    logger.debug("DB Auth Token: {}", dbUser.getAuthToken());
                    logger.debug("Token Auth Token: {}", authUser.getAuthToken());
                }
                System.out.println(authUser.getAuthToken()+" "+dbUser.getAuthToken());
                if (dbUser != null && dbUser.getAuthToken() != null &&
                        dbUser.getAuthToken().equals(authUser.getAuthToken())) {

                    logger.debug("Auth token matches. Setting security context for user: {}", dbUser.getLogin());

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            dbUser, null, dbUser.getUserRole().getGrantedAuthorities()
                    );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                } else {
                    logger.warn("Invalid JWT token or auth token mismatch for user: {}",
                            authUser != null ? authUser.getLogin() : "unknown");
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid JWT token");
                    return;
                }

            } else {
                logger.warn("JWT token validation failed.");
            }

        } else {
            logger.debug("No JWT token found in request.");
        }

        logger.debug("Continuing filter chain for URI: {}", request.getRequestURI());
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        logger.debug("Authorization header: {}", bearerToken);

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            logger.debug("Extracted JWT token from header.");
            return token;
        }
        return null;
    }
}
