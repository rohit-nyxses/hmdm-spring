package com.hmdm.rest.json;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * The credentials to be used for authenticating the user to the application.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCredentials implements Serializable {

    private static final long serialVersionUID = 7107010132749776504L;

    @JsonAlias({"login", "username"})
    @JsonProperty("login")
    private String login;


    @JsonProperty("password")
    private String password;

    /**
     * @deprecated This field is deprecated and will be removed in future versions.
     */
    @Deprecated
    @JsonProperty("email")
    private String email;

    public UserCredentials() {
        // Default constructor
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Deprecated
    public String getEmail() {
        return email;
    }

    @Deprecated
    public void setEmail(String email) {
        this.email = email;
    }
}
