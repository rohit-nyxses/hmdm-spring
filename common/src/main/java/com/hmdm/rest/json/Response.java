package com.hmdm.rest.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response implements Serializable {

    private static final long serialVersionUID = 3268801711912541479L;

    private ResponseStatus status;
    private String message;
    private Object data;

    // Factory methods
    public static Response OK(String message, Object data) {
        return new Response(ResponseStatus.OK, message, data);
    }

    public static Response OK(Object data) {
        return OK(null, data);
    }

    public static Response OK(String message) {
        return OK(message, null);
    }

    public static Response OK() {
        return OK(null, null);
    }

    public static Response WARNING(String message, Object data) {
        return new Response(ResponseStatus.WARNING, message, data);
    }
    public static Response PERMISSION_DENIED() {
        return ERROR("error.permission.denied", null);
    }

    public static Response WARNING(Object data) {
        return WARNING(null, data);
    }

    public static Response WARNING(String message) {
        return WARNING(message, null);
    }

    public static Response WARNING() {
        return WARNING(null, null);
    }

    public static Response ERROR(String message, Object data) {
        return new Response(ResponseStatus.ERROR, message, data);
    }

    public static Response ERROR(Object data) {
        return ERROR(null, data);
    }

    public static Response ERROR(String message) {
        return ERROR(message, null);
    }

    public static Response ERROR() {
        return ERROR(null, null);
    }

    public enum ResponseStatus {
        OK,
        WARNING,
        ERROR
    }
}
