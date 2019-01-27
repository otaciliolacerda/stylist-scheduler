package com.otacilio.challange.stylistscheduler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Past dates are not allowed...")
public class InvalidDateException extends Exception {

    public InvalidDateException(String message) {
        super(message);
    }

}
