package com.otacilio.challange.stylistscheduler.service;

import com.otacilio.challange.stylistscheduler.exception.InvalidDateException;
import com.otacilio.challange.stylistscheduler.exception.InvalidTimeSlotException;
import com.otacilio.challange.stylistscheduler.exception.ResourceNotFoundException;
import com.otacilio.challange.stylistscheduler.model.TimeSlot;
import com.otacilio.challange.stylistscheduler.repository.TimeSlotRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeSlotService {

    private TimeSlotRepository repository;

    public TimeSlotService(TimeSlotRepository repository) {
        this.repository = repository;
    }

    public TimeSlot find(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
    }

    public List<TimeSlot> findAll() {
        return repository.findAll();
    }

    public TimeSlot create(TimeSlot timeSlot) throws InvalidTimeSlotException, InvalidDateException {
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

    /**
     * Get all available time slots in the next 7 days starting from 'date'. Past dates or dates more than 30 days
     * in the future are invalid
     *
     * @param date Starting date
     * @return All available time slots in the time frame
     */
    public List<TimeSlot> findAvailable(LocalDate date) throws InvalidDateException {
        if (date.isBefore(LocalDate.now()) || date.isAfter(LocalDate.now().plusDays(30))) {
            throw new InvalidDateException("Invalid date");
        }
        return repository.findAllByDateBetweenAndAppointmentIsNull(date, date.plusDays(6))
                .stream().distinct().collect(Collectors.toList());
    }
}
