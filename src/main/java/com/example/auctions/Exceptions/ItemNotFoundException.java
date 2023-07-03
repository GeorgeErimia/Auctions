package com.example.auctions.Exceptions;

public class ItemNotFoundException extends RuntimeException{
    private static final String message = "Item not found";

    public ItemNotFoundException() {
        super(message);
    }

    public ItemNotFoundException(String message) {
        super(message);
    }

    public ItemNotFoundException(Throwable cause) {
        super(cause);
    }
}
