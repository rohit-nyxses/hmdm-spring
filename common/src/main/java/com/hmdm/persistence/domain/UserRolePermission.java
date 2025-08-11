package com.hmdm.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a permission that can be granted to a user role.
 */
public class UserRolePermission implements Serializable {

    private static final long serialVersionUID = -8203664108953283604L;

    private int id;
    private String name;
    private String description;

    @JsonIgnore
    private boolean superAdmin;

    public UserRolePermission() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRolePermission)) return false;
        UserRolePermission that = (UserRolePermission) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserRolePermission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", superAdmin=" + superAdmin +
                '}';
    }
}
