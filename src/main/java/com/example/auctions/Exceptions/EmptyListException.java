package com.example.auctions.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EmptyListException extends RuntimeException{
    private static final String message = "List is empty";
    public EmptyListException() {
        super(message);
    }

    public EmptyListException(String message) {
        super(message);
    }

    public EmptyListException(Throwable cause) {
        super(message, cause);
    }
}
