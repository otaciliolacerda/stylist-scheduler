package com.otacilio.challange.stylistscheduler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid time slot or time slot already exists.")
public class InvalidTimeSlotException extends Exception {

    public InvalidTimeSlotException(String message) {
        super(message);
    }

}
