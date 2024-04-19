package com.servifix.restapi.shared.exception;

public class InvalidGenderException extends RuntimeException{
    public InvalidGenderException(String message){
        super(message);
    }
}
