package com.wolox.training.constants;

public class ErrorMessages {
    public static final  String internalServerErrorMessage = "Internal server error";
    public static final  String notFoundBookErrorMessage = "Book not found";

    private ErrorMessages(){
        throw new AssertionError();
    }
}
