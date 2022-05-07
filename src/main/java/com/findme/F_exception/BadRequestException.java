package com.findme.F_exception;


public class BadRequestException extends Exception{

    public BadRequestException(String message) {
        super(message);
    }
}
