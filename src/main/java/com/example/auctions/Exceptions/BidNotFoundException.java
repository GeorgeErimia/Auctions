package com.example.auctions.Exceptions;

public class BidNotFoundException extends RuntimeException{
    private static String message = "Bid not found";

    public BidNotFoundException() {
    }

    public BidNotFoundException(String message) {
        super(message);
    }

    public BidNotFoundException(Throwable cause) {
        super(cause);
    }
}
