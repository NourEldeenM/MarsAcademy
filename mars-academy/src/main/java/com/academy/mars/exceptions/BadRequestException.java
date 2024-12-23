package com.academy.mars.exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}
