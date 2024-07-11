package com.Hospital.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

//Estructura de datos para devolver Errores
public class ErrorDetails {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;

    public ErrorDetails(int status, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = HttpStatus.valueOf(status).getReasonPhrase();
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}