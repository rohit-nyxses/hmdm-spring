//package com.hmdm.security.jwt.rest;
//import com.hmdm.persistence.CustomerDAO;
//import com.hmdm.persistence.UnsecureDAO;
//import com.hmdm.persistence.domain.User;
//import com.hmdm.rest.json.UserCredentials;
//import com.hmdm.security.jwt.TokenProvider;
//import com.hmdm.security.jwt.rest.JWTToken;
//import com.hmdm.util.BackgroundTaskRunnerService;
//import com.hmdm.util.PasswordUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//
//
//@RestController
//@RequestMapping("/public/jwt")
//public class JWTAuthResource {
//
//    private static final String AUTHORIZATION_HEADER = "Authorization";
//    private static final Logger log = LoggerFactory.getLogger("JWTAuth");
//
//    private final TokenProvider tokenProvider;
//    private final UnsecureDAO unsecureDAO;
//    private final CustomerDAO customerDAO;
//    private final BackgroundTaskRunnerService taskRunner;
//
//    @Autowired
//    public JWTAuthResource(TokenProvider tokenProvider,
//                             UnsecureDAO unsecureDAO,
//                             CustomerDAO customerDAO,
//                             BackgroundTaskRunnerService taskRunner) {
//        this.tokenProvider = tokenProvider;
//        this.unsecureDAO = unsecureDAO;
//        this.customerDAO = customerDAO;
//        this.taskRunner = taskRunner;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<JWTToken> login(@RequestBody UserCredentials credentials) {
//        System.out.println("🔵 Login endpoint hit with credentials: " + credentials.getLogin());
//        try {
//
//            if (credentials.getLogin() == null || credentials.getPassword() == null) {
//                return ResponseEntity.badRequest().build();
//            }
//
//            User user = unsecureDAO.findByLoginOrEmail(credentials.getLogin());
//            System.out.println(user.getLogin());
//            System.out.println("🔍 Input password (plain): " + credentials.getPassword());
//            System.out.println("🔍 DB password (hashed): " + user.getPassword());
//
//            boolean matched = PasswordUtil.passwordMatch(credentials.getPassword(), user.getPassword());
//            System.out.println("✅ Password match result: " + matched);
//
//
//            if (user.getLogin() == null || user.getPassword() == null || !matched) {
//                System.out.println("❌ Login failed: user not found or password mismatch");
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//            }
//            System.out.println("User not Null and password matched");
//
//            taskRunner.submitTask(() ->
//                    customerDAO.recordLastLoginTime(user.getCustomerId(), System.currentTimeMillis())
//            );
//
//            if (user.getAuthToken() == null || user.getAuthToken().isEmpty()) {
//                user.setAuthToken(PasswordUtil.generateToken());
//                user.setNewPassword(user.getPassword());
//                unsecureDAO.setUserNewPasswordUnsecure(user);
//            }
//            user.setPassword(null);
//
//            String token = tokenProvider.createToken(user, false);
//            JWTToken result = new JWTToken(token);
//
//            return ResponseEntity.ok()
//                    .header(AUTHORIZATION_HEADER, "Bearer " + token)
//                    .body(result);
//
//        } catch (Exception e) {
//            log.error("Unexpected error when authenticating user", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//}

// spring managed bean
// Updated JWTAuthResource.java
package com.hmdm.jwt.rest;

import com.hmdm.persistence.CustomerDAO;
import com.hmdm.persistence.UnsecureDAO;
import com.hmdm.persistence.domain.User;
import com.hmdm.rest.json.UserCredentials;
import com.hmdm.jwt.TokenProvider;
import com.hmdm.util.BackgroundTaskRunnerService;
import com.hmdm.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/jwt")
public class JWTAuthResource {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final Logger log = LoggerFactory.getLogger("JWTAuth");

    private final TokenProvider tokenProvider;
    private final UnsecureDAO unsecureDAO;
    private final CustomerDAO customerDAO;
    private final BackgroundTaskRunnerService taskRunner;
    private final PasswordUtil passwordUtil;

    @Autowired
    public JWTAuthResource(TokenProvider tokenProvider,
                           UnsecureDAO unsecureDAO,
                           CustomerDAO customerDAO,
                           BackgroundTaskRunnerService taskRunner,
                           PasswordUtil passwordUtil) {
        this.tokenProvider = tokenProvider;
        this.unsecureDAO = unsecureDAO;
        this.customerDAO = customerDAO;
        this.taskRunner = taskRunner;
        this.passwordUtil = passwordUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<JWTToken> login(@RequestBody UserCredentials credentials) {
        System.out.println("\uD83D\uDD35 Login endpoint hit with credentials: " + credentials.getLogin());
        try {
            if (credentials.getLogin() == null || credentials.getPassword() == null) {
                return ResponseEntity.badRequest().build();
            }

            User user = unsecureDAO.findByLoginOrEmail(credentials.getLogin());
            System.out.println(user.getLogin());


            boolean matched = passwordUtil.passwordMatch(credentials.getPassword(), user.getPassword());

            if (user.getLogin() == null || user.getPassword() == null || !matched) {
//                System.out.println("\u274C Login failed: user not found or password mismatch");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
//            System.out.println("User not Null and password matched");

            taskRunner.submitTask(() ->{
                customerDAO.recordLastLoginTime(user.getCustomerId(), System.currentTimeMillis());
                customerDAO.printLastLoginTime(user.getCustomerId());
            });

            if (user.getAuthToken() == null || user.getAuthToken().isEmpty()) {
                user.setAuthToken(passwordUtil.generateToken());
                System.out.println(user.getAuthToken());
                user.setNewPassword(user.getPassword());
                unsecureDAO.setUserNewPasswordUnsecure(user);
            }
            user.setPassword(null);

            String token = tokenProvider.createToken(user, credentials.getRemember());
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
