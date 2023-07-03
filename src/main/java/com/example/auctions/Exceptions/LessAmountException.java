package com.example.auctions.Exceptions;

public class LessAmountException extends RuntimeException{
    private static final String message = "Amount is less than the current bid";

    public LessAmountException() {
    }

    public LessAmountException(String message) {
        super(message);
    }

    public LessAmountException(Throwable cause) {
        super(cause);
    }
}
