package com.otacilio.challange.stylistscheduler.service;

import com.otacilio.challange.stylistscheduler.model.Appointment;
import com.otacilio.challange.stylistscheduler.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        this.repository = repository;
    }

    public Appointment find(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Appointment> findAll() {
        return repository.findAll();
    }

    public Appointment create(Appointment appointment) {
        Appointment new_appointment = repository.save(appointment);
        return new_appointment;
    }
}
