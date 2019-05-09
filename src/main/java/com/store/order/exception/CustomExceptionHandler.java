package com.store.order.exception;

import com.store.order.model.response.ErrorResponse;
import com.store.order.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OrderCreationFailedException.class)
    public final ResponseEntity<ErrorResponse> failedRequest(OrderCreationFailedException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage(), AppConstants.ORDER_CREATION_FAILED, AppConstants.SERVER_ERROR);
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleAllExceptions(OrderNotFoundException e) {
        ErrorResponse error = new ErrorResponse(e.getMessage(), AppConstants.ORDER_NOT_FOUND, AppConstants.NOT_FOUND);
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserCreationFailedException.class)
    public final ResponseEntity<ErrorResponse> handleUserCreationException(UserCreationFailedException e) {
        ErrorResponse error = new ErrorResponse(e.getMessage(), AppConstants.FAILED_USER_CREATION, AppConstants.SERVER_ERROR);
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

}
