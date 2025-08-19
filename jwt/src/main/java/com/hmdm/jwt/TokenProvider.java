

package com.hmdm.jwt;

import com.hmdm.persistence.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class TokenProvider {

    private static final Logger log = LoggerFactory.getLogger(TokenProvider.class);
    private static final String TOKEN_KEY = "token";

    @Value("${jwt.secret:#{null}}")
    private String secret;

    @Value("${jwt.token-validity-in-seconds:86400}")
    private long tokenValidityInSeconds;

    @Value("${jwt.token-validity-in-seconds-remember-me:2592000}")
    private long tokenValidityInSecondsForRememberMe;

    private Key key;



    @PostConstruct
    public void init() {

            log.info("Using JWT secret: {}", secret);
            this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            log.info("JWT key initialized: {}", key);


    }

    public String createToken(User user, boolean rememberMe) {
        long now = System.currentTimeMillis();
        long validityInMillis = rememberMe ? tokenValidityInSecondsForRememberMe * 1000 : tokenValidityInSeconds * 1000;
        Date expiration = new Date(now + validityInMillis);
        System.out.print(expiration);

        System.out.println(user.getAuthToken());
        return Jwts.builder()
                .setSubject(user.getLogin())
                .claim(TOKEN_KEY, user.getAuthToken())
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(expiration)
                .compact();
    }


    public User getAuthentication(String jwtToken) throws IOException {
        try {
            // First verify the signature
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();

            // Then extract user info
            User user = new User();
            user.setLogin(claims.getSubject());
            user.setAuthToken(claims.get(TOKEN_KEY, String.class));

            log.debug("Authenticated user: {}", user.getLogin());
            return user;
        } catch (SignatureException e) {
            log.error("JWT signature verification failed");
            log.error("Current key: {}", key);
            throw new SecurityException("Invalid token signature");
        }
    }
    public boolean validateToken(String jwtToken) {

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("JWT validation error: {}", e.getMessage());
            return false;
        }
    }
}
