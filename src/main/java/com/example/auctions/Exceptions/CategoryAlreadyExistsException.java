package com.example.auctions.Exceptions;

public class CategoryAlreadyExistsException extends RuntimeException{
    private static final String MESSAGE = "Category already exists";

    public CategoryAlreadyExistsException() {
        super(MESSAGE);
    }

    public CategoryAlreadyExistsException(String message) {
        super(message);
    }

    public CategoryAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
