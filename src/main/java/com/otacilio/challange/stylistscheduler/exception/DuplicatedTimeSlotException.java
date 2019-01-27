package com.otacilio.challange.stylistscheduler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "TimeSlot already existss...")
public class DuplicatedTimeSlotException extends Exception {

    public DuplicatedTimeSlotException(String message) {
        super(message);
    }

}
