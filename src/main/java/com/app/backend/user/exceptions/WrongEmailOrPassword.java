package com.app.backend.user.exceptions;

public class WrongEmailOrPassword extends RuntimeException {
    public WrongEmailOrPassword(String message) {
        super(message);
    }
}
