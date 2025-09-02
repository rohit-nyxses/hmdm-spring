package com.hmdm.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Data                               // Generates Getters, Setters, equals, hashCode, toString
@NoArgsConstructor                  // Generates a No-Args Constructor
@AllArgsConstructor                // Generates an All-Args Constructor
public class UserRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 5723531788626059664L;

    private Integer id;
    private String name;
    private String description;

    @JsonIgnore
    private boolean superAdmin;

    private List<UserRolePermission> permissions;

    // Custom method must stay
    public Collection<? extends GrantedAuthority> getGrantedAuthorities() {
        if (permissions == null) {
            return Collections.emptyList();
        }
        return permissions.stream()
                .map(p -> (GrantedAuthority) () -> p.getName())
                .collect(Collectors.toList());
    }
}
