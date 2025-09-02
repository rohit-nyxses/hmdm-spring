package com.hmdm.persistence.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {

    private static final long serialVersionUID = -5231237331183323703L;

    private Integer id;
    private String login;
    private String email;
    private String name;

    // Password should not be serialized
    private transient String password;

    private int customerId;
    private UserRole userRole;

    @Builder.Default
    private boolean allDevicesAvailable = true;

    @Builder.Default
    private boolean allConfigAvailable = true;

    @Builder.Default
    private boolean passwordReset = false;

    private String authToken;
    private String passwordResetToken;
    private String authData;
    private String twoFactorSecret;
    private boolean twoFactorAccepted;

    @Builder.Default
    private boolean twoFactor = false;

    private Integer idleLogout;
    private long lastLoginFail;

    private String oldPassword;
    private String newPassword;

    @Builder.Default
    private boolean masterCustomer = false;

    @Builder.Default
    private boolean editable = false;

    @Builder.Default
    private boolean singleCustomer = false;

    // Custom toString() (Lombok’s @Data already gives one, but we want to keep the business-specific format)
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getName());
        builder.append(' ');
        builder.append(this.getLogin());
        builder.append(' ');
        builder.append(this.getEmail());
        if (this.getUserRole() != null) {
            builder.append(' ');
            builder.append(this.getUserRole().getName());
        }
        return builder.toString();
    }
}
