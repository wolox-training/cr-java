package com.wolox.training.exceptions;

public class BookAlreadyOwnException extends RuntimeException{
    public BookAlreadyOwnException(String message){
        super(message);
    }
}
