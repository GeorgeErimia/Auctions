package com.example.auctions.Exceptions;

import com.example.auctions.Model.dtos.ProfileDTO;

public class ProfileNotFoundException extends RuntimeException{
    private static final String MESSAGE = "Profile not found";
    public ProfileNotFoundException() {
        super(MESSAGE);
    }

    public ProfileNotFoundException(String message){
        super(message);
    }

    public ProfileNotFoundException(Throwable cause){
        super(cause);
    }
}
