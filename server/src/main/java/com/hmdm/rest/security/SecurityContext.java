package com.hmdm.rest.security;

import com.hmdm.persistence.domain.User;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Slf4j
@UtilityClass
public class SecurityContext {

    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public Optional<Integer> getCurrentCustomerId() {
        return getCurrentUser().map(User::getCustomerId);
    }

    public String getCurrentUserName() {
        return getCurrentUser().map(User::getLogin).orElse("null");
    }

    public boolean isSuperAdmin() {
        return getCurrentUser().map(user -> user.getUserRole().isSuperAdmin()).orElse(false);
    }

    public boolean hasPermission(String permission) {
        return isSuperAdmin() ||
                getCurrentUser().map(user -> user.getUserRole().getPermissions()
                                .stream()
                                .anyMatch(p -> p.getName().equalsIgnoreCase(permission)))
                        .orElse(false);
    }


}
