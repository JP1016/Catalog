package com.store.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserCreationFailedException extends RuntimeException
{
    public UserCreationFailedException(String exception) {
        super(exception);
    }
}