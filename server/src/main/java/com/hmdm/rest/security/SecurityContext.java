package com.hmdm.rest.security;

import com.hmdm.persistence.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityContext {

    public static Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public static Optional<Integer> getCurrentCustomerId() {
        return getCurrentUser()
                .map(User::getCustomerId);
    }

    public static String getCurrentUserName() {
        return getCurrentUser()
                .map(User::getLogin)
                .orElse("null");
    }

    public static boolean isSuperAdmin() {
        return getCurrentUser()
                .map(user -> user.getUserRole().isSuperAdmin())
                .orElse(false);
    }

    public static boolean hasPermission(String permission) {
        return isSuperAdmin() ||
                getCurrentUser()
                        .map(user -> user.getUserRole().getPermissions()
                                .stream()
                                .anyMatch(p -> p.getName().equalsIgnoreCase(permission)))
                        .orElse(false);
    }

    // Deprecated legacy init/release methods — not needed in Spring
    @Deprecated
    public static void init(User user) {
        throw new UnsupportedOperationException("Use Spring Security instead");
    }

    @Deprecated
    public static void init(Integer customerId) {
        throw new UnsupportedOperationException("Use Spring Security instead");
    }

    @Deprecated
    public static void release() {
        // No-op
    }
}
