package com.example.auctions.Exceptions;

public class BidBySameUser extends RuntimeException{
    private static final String message = "Bid is from the same user";

    public BidBySameUser() {
    }

    public BidBySameUser(String message) {
        super(message);
    }

    public BidBySameUser(Throwable cause) {
        super(cause);
    }
}
