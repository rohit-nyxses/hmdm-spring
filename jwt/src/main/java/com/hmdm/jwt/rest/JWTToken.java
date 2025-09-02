package com.hmdm.jwt.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * A JWT token identifying a single client of the application.
 */
@NoArgsConstructor
public class JWTToken implements Serializable {

    @Serial
    private static final long serialVersionUID = -2577292299714311437L;

    @JsonProperty("id_token")
    private String idToken;


    public JWTToken(String idToken) {
        this.idToken = idToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
