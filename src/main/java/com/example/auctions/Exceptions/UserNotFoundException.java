package com.example.auctions.Exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

public class UserNotFoundException extends RuntimeException{
    private static final String message = "User not found";
    public UserNotFoundException() {
        super(message);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Throwable cause) {
        super(message, cause);
    }
}
