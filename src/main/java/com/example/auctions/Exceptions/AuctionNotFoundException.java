package com.example.auctions.Exceptions;

public class AuctionNotFoundException extends RuntimeException{
    private static final String message = "Auction not found";

    public AuctionNotFoundException() {
        super(message);
    }

    public AuctionNotFoundException(String message) {
        super(message);
    }

    public AuctionNotFoundException(Throwable cause) {
        super(cause);
    }
}
