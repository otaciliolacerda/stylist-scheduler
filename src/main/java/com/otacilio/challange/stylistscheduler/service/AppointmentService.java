package com.otacilio.challange.stylistscheduler.service;

import com.otacilio.challange.stylistscheduler.dto.AvailableTimeSlot;
import com.otacilio.challange.stylistscheduler.exception.InvalidAppointmentException;
import com.otacilio.challange.stylistscheduler.exception.ResourceNotFoundException;
import com.otacilio.challange.stylistscheduler.exception.TimeSlotNotAvailableException;
import com.otacilio.challange.stylistscheduler.model.Appointment;
import com.otacilio.challange.stylistscheduler.model.Customer;
import com.otacilio.challange.stylistscheduler.model.TimeSlot;
import com.otacilio.challange.stylistscheduler.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TimeSlotService timeSlotService;

    public Appointment find(Long id) {
        return appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Transactional(rollbackFor = InvalidAppointmentException.class) // Allocating
    // shared resource "TimeSlot"
    public Appointment create(Customer customer, AvailableTimeSlot aTS) throws TimeSlotNotAvailableException,
            InvalidAppointmentException {

        try {
            customer = customerService.find(customer.getId());
        } catch (Exception e) {
            throw new InvalidAppointmentException("Invalid customer");
        }

        // Find a free time slot
        List<TimeSlot> slots = timeSlotService.findAll(aTS.getDate(), aTS.getStart(), aTS.getEnd());

        if (slots.isEmpty()) {
            throw new TimeSlotNotAvailableException("Time slot not available");
        }

        try {
            return appointmentRepository.save(new Appointment(customer, slots.get(0)));
        } catch (DataIntegrityViolationException e) {
            throw new InvalidAppointmentException("Invalid appointment");
        }
    }
}
