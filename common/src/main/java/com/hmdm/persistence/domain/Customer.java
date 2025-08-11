package com.hmdm.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * A customer account managed by the application.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer implements Serializable {

    @Serial
    private static final long serialVersionUID = 5087620848737792920L;

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

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFilesDir() { return filesDir; }
    public void setFilesDir(String filesDir) { this.filesDir = filesDir; }

    public boolean isMaster() { return master; }
    public void setMaster(boolean master) { this.master = master; }

    public String getPrefix() { return prefix; }
    public void setPrefix(String prefix) { this.prefix = prefix; }

    public Integer getDeviceConfigurationId() { return deviceConfigurationId; }
    public void setDeviceConfigurationId(Integer deviceConfigurationId) { this.deviceConfigurationId = deviceConfigurationId; }

    public Long getLastLoginTime() { return lastLoginTime; }
    public void setLastLoginTime(Long lastLoginTime) { this.lastLoginTime = lastLoginTime; }

    public Long getRegistrationTime() { return registrationTime; }
    public void setRegistrationTime(Long registrationTime) { this.registrationTime = registrationTime; }

    public boolean isCopyDesign() { return copyDesign; }
    public void setCopyDesign(boolean copyDesign) { this.copyDesign = copyDesign; }

    public Integer[] getConfigurationIds() { return configurationIds; }
    public void setConfigurationIds(Integer[] configurationIds) { this.configurationIds = configurationIds; }

    public Long getExpiryTime() { return expiryTime; }
    public void setExpiryTime(Long expiryTime) { this.expiryTime = expiryTime; }

    public int getDeviceLimit() { return deviceLimit; }
    public void setDeviceLimit(int deviceLimit) { this.deviceLimit = deviceLimit; }

    public int getSizeLimit() { return sizeLimit; }
    public void setSizeLimit(int sizeLimit) { this.sizeLimit = sizeLimit; }

    public int getAccountType() { return accountType; }
    public void setAccountType(int accountType) { this.accountType = accountType; }

    public String getCustomerStatus() { return customerStatus; }
    public void setCustomerStatus(String customerStatus) { this.customerStatus = customerStatus; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public int getInactiveState() { return inactiveState; }
    public void setInactiveState(int inactiveState) { this.inactiveState = inactiveState; }

    public int getPauseState() { return pauseState; }
    public void setPauseState(int pauseState) { this.pauseState = pauseState; }

    public int getAbandonState() { return abandonState; }
    public void setAbandonState(int abandonState) { this.abandonState = abandonState; }

    public String getSignupStatus() { return signupStatus; }
    public void setSignupStatus(String signupStatus) { this.signupStatus = signupStatus; }

    public String getSignupToken() { return signupToken; }
    public void setSignupToken(String signupToken) { this.signupToken = signupToken; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", filesDir='" + filesDir + '\'' +
                ", master=" + master +
                ", prefix='" + prefix + '\'' +
                ", deviceConfigurationId=" + deviceConfigurationId +
                ", accountType=" + accountType +
                ", deviceLimit=" + deviceLimit +
                ", sizeLimit=" + sizeLimit +
                ", customerStatus='" + customerStatus + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", language='" + language + '\'' +
                ", inactiveState=" + inactiveState +
                ", pauseState=" + pauseState +
                ", abandonState=" + abandonState +
                ", signupStatus='" + signupStatus + '\'' +
                ", signupToken='" + signupToken + '\'' +
                '}';
    }
}
