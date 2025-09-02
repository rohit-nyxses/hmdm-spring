package com.hmdm.jwt.rest;

import com.hmdm.persistence.CustomerDAO;
import com.hmdm.persistence.UnsecureDAO;
import com.hmdm.persistence.domain.User;
import com.hmdm.rest.json.UserCredentials;
import com.hmdm.jwt.TokenProvider;
import com.hmdm.util.BackgroundTaskRunnerService;
import com.hmdm.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/public/jwt")
@RequiredArgsConstructor
public class JWTAuthResource {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenProvider tokenProvider;
    private final UnsecureDAO unsecureDAO;
    private final CustomerDAO customerDAO;
    private final BackgroundTaskRunnerService taskRunner;
    private final PasswordUtil passwordUtil;

    @PostMapping("/login")
    public ResponseEntity<JWTToken> login(@RequestBody UserCredentials credentials) {
        log.info("🔵 Login endpoint hit with credentials: {}", credentials.getLogin());

        try {
            if (credentials.getLogin() == null || credentials.getPassword() == null) {
                return ResponseEntity.badRequest().build();
            }

            User user = unsecureDAO.findByLoginOrEmail(credentials.getLogin());
            if (user == null) {
                log.warn("⚠️ User not found for login: {}", credentials.getLogin());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            boolean matched = passwordUtil.passwordMatch(credentials.getPassword(), user.getPassword());
            if (!matched) {
                log.warn("⚠️ Password mismatch for user: {}", credentials.getLogin());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Record last login asynchronously
            taskRunner.submitTask(() -> {
                customerDAO.recordLastLoginTime(user.getCustomerId(), System.currentTimeMillis());
                customerDAO.printLastLoginTime(user.getCustomerId());
            });

            // Generate auth token if not already set
            if (user.getAuthToken() == null || user.getAuthToken().isEmpty()) {
                user.setAuthToken(passwordUtil.generateToken());
                log.info("Generated new auth token for user: {}", user.getLogin());
                user.setNewPassword(user.getPassword());
                unsecureDAO.setUserNewPasswordUnsecure(user);
            }

            user.setPassword(null); // remove password before sending user data
            String token = tokenProvider.createToken(user, credentials.isRemember());
            JWTToken result = new JWTToken(token);

            return ResponseEntity.ok()
                    .header(AUTHORIZATION_HEADER, "Bearer " + token)
                    .body(result);

        } catch (Exception e) {
            log.error("Unexpected error when authenticating user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
