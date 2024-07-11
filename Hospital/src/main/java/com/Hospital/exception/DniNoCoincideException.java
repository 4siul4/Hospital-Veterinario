package com.Hospital.exception;

public class DniNoCoincideException extends RuntimeException{
    public DniNoCoincideException(String message) {
        super(message);
    }
}