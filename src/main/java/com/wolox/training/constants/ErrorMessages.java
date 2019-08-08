package com.wolox.training.constants;

public class ErrorMessages {
    public static final  String internalServerErrorMessage = "Internal server error";
    public static final  String notFoundBookErrorMessage = "Book not found";
    public static final  String notFoundUserErrorMessage = "User not found";
    public static final  String bookAlreadyOwnErrorMessage = "User already has this book";

    private ErrorMessages(){
        throw new AssertionError();
    }
}
