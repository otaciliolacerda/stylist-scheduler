package com.otacilio.challange.stylistscheduler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Time slot not available")
public class TimeSlotNotAvailableException extends Exception {

    public TimeSlotNotAvailableException(String message) {
        super(message);
    }

}
