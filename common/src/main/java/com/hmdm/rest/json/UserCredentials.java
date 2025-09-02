package com.hmdm.rest.json;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The credentials to be used for authenticating the user to the application.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCredentials implements Serializable {

    private static final long serialVersionUID = 7107010132749776504L;

    @JsonAlias({"login", "username"})
    @JsonProperty("login")
    private String login;

    @JsonProperty("password")
    private String password;

    @JsonProperty("remember")
    private boolean remember;

    /**
     * @deprecated This field is deprecated and will be removed in future versions.
     */
    @Deprecated
    @JsonProperty("email")
    private String email;
}
