package com.wolox.training.exceptions;

public class ServerErrorException extends RuntimeException{
    public ServerErrorException(String message){
        super(message);
    }
}
