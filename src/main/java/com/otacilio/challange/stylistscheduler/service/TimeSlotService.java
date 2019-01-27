package com.otacilio.challange.stylistscheduler.service;

import com.otacilio.challange.stylistscheduler.model.Appointment;
import com.otacilio.challange.stylistscheduler.model.TimeSlot;
import com.otacilio.challange.stylistscheduler.repository.AppointmentRepository;
import com.otacilio.challange.stylistscheduler.repository.TimeSlotRepository;
import org.springframework.stereotype.Service;

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

    public TimeSlot create(TimeSlot timeSlot) {
        TimeSlot new_timeslot = repository.save(timeSlot);
        return new_timeslot;
    }
}
