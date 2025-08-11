package com.hmdm.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a user role in the system.
 */
public class UserRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 5723531788626059664L;

    private Integer id;
    private String name;
    private String description;

    @JsonIgnore
    private boolean superAdmin;

    private List<UserRolePermission> permissions;

    public UserRole() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        this.superAdmin = superAdmin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UserRolePermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<UserRolePermission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRole)) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equals(id, userRole.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", superAdmin=" + superAdmin +
                '}';
    }

    public Collection<? extends GrantedAuthority> getGrantedAuthorities() {
        if (permissions == null) {
            return Collections.emptyList();
        }
        return permissions.stream()
                .map(p -> (GrantedAuthority) () -> p.getName())
                .collect(Collectors.toList());
    }

}
