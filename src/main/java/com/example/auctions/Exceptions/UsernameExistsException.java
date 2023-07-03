package com.example.auctions.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UsernameExistsException extends Exception{

    private static final String message = "Username already exists";

    public UsernameExistsException(){
        super(message);
    }

    public UsernameExistsException(String message){
        super(message);
    }

    public UsernameExistsException(String message, Throwable cause){
        super(message, cause);
    }
}
