package com.wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ NotFoundException.class })
    public final ResponseEntity<ErrorDetails> handleUserNotFoundException(NotFoundException notFound, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(notFound.getMessage(),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ BadRequestException.class })
    public final ResponseEntity<ErrorDetails> handleUserNotFoundException(BadRequestException badRequest, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(badRequest.getMessage(),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ ServerErrorException.class })
    public final ResponseEntity<ErrorDetails> handleServerErrorException(ServerErrorException serverError, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(serverError.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
