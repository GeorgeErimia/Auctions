package com.example.auctions.Exceptions;

public class UpdateFailedException extends RuntimeException{
    private static final String message = "Update failed";

    public UpdateFailedException() {
    }

    public UpdateFailedException(String message) {
        super(message);
    }

    public UpdateFailedException(Throwable cause) {
        super(cause);
    }
}
