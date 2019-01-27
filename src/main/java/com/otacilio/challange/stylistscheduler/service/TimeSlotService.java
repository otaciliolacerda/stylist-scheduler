package com.otacilio.challange.stylistscheduler.service;

import com.otacilio.challange.stylistscheduler.exception.InvalidDateException;
import com.otacilio.challange.stylistscheduler.exception.InvalidTimeSlotException;
import com.otacilio.challange.stylistscheduler.model.TimeSlot;
import com.otacilio.challange.stylistscheduler.repository.TimeSlotRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class TimeSlotService {

    private TimeSlotRepository repository;

    public TimeSlotService(TimeSlotRepository repository) {
        this.repository = repository;
    }

    public TimeSlot find(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<TimeSlot> findAll() {
        return repository.findAll();
    }

    public TimeSlot create(TimeSlot timeSlot) throws InvalidTimeSlotException, InvalidDateException {
        /**
         * Validation:
         * 1 - Must not allow past dates
         * 2 - Must not allow duplicated time slots to the same stylist
         */
        if (timeSlot.getDate().isBefore(LocalDate.now()) ||
                (timeSlot.getDate().isEqual(LocalDate.now()) && timeSlot.getStart().isBefore(LocalTime.now()))) {
            throw new InvalidDateException("It is not possible to create time slots for past dates");
        }

        try {
            return repository.save(timeSlot);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidTimeSlotException("Invalid TimeSlot");
        }
    }
}
