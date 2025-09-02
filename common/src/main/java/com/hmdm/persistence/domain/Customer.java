package com.hmdm.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import java.io.Serial;
import java.io.Serializable;

/**
 * A customer account managed by the application.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Customer implements Serializable {

    @Serial
    private static final long serialVersionUID = 5087620848737792920L;

    @EqualsAndHashCode.Include
    private Integer id;

    private String name;
    private String email;
    private String description;
    private String filesDir;
    private boolean master = false;

    public static final int DEMO = 0;
    public static final int SMALL_BUSINESS = 1;
    public static final int ENTERPRISE = 2;
    public static final int PRIMARY = 3;

    public static final String STATUS_NEW = "customer.new";
    public static final String STATUS_ACTIVE = "customer.active";
    public static final String STATUS_NEED_FOLLOWUP = "customer.need.followup";
    public static final String STATUS_FOLLOWUP_SENT = "customer.followup.sent";
    public static final String STATUS_INTERNAL_TEST = "customer.internal.test";
    public static final String STATUS_DEVELOPER = "customer.developer";
    public static final String STATUS_DIFFICULT = "customer.difficult";
    public static final String STATUS_INACTIVE = "customer.inactive";
    public static final String STATUS_PAUSE = "customer.pause";
    public static final String STATUS_ABANDON = "customer.abandon";
    public static final String STATUS_DENIAL = "customer.denial";
    public static final String STATUS_ONPREMISE = "customer.onpremise";
    public static final String STATUS_CLIENT = "customer.client";

    private String prefix;
    private Integer deviceConfigurationId;
    private Long lastLoginTime;
    private Long registrationTime;
    private boolean copyDesign;
    private Integer[] configurationIds;
    private Long expiryTime;
    private int deviceLimit;
    private int sizeLimit;
    private int accountType;
    private String customerStatus;
    private String firstName;
    private String lastName;
    private String language;
    private int inactiveState;
    private int pauseState;
    private int abandonState;
    private String signupStatus;
    private String signupToken;
}
