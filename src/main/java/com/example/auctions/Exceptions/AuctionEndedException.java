package com.example.auctions.Exceptions;

public class AuctionEndedException extends RuntimeException{
    private static final String message = "Auction has ended";
    public AuctionEndedException(String message) {
        super(message);
    }
    public AuctionEndedException() {
        super(message);
    }

    public AuctionEndedException(Throwable cause) {
        super(cause);
    }
}
