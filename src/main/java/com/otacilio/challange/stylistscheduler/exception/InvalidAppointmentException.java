package com.otacilio.challange.stylistscheduler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid appointment")
public class InvalidAppointmentException extends Exception {

    public InvalidAppointmentException(String message) {
        super(message);
    }

}
