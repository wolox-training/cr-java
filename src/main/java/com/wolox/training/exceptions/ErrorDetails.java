package com.wolox.training.exceptions;

import org.springframework.http.HttpStatus;

public class ErrorDetails {
    private HttpStatus status;
    private String message;


    public ErrorDetails(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
