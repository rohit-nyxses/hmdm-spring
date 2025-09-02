package com.hmdm.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;

/**
 * Represents a permission that can be granted to a user role.
 */
@Data                     // Generates getters, setters, equals, hashCode, toString
@NoArgsConstructor         // Generates no-args constructor
@AllArgsConstructor        // Generates all-args constructor
@Builder                  // Optional: enables builder pattern
public class UserRolePermission implements Serializable {

    private static final long serialVersionUID = -8203664108953283604L;

    private int id;
    private String name;
    private String description;

    @JsonIgnore
    private boolean superAdmin;
}
