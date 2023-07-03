package com.example.auctions.Exceptions;

public class CategoryNotFoundException extends Exception{

    private static final String message = "Category not found";

    public CategoryNotFoundException() {
        super(message);
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(Throwable cause) {
        super(cause);
    }
}
