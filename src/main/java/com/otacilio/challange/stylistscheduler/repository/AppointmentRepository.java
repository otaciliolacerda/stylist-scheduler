package com.otacilio.challange.stylistscheduler.repository;

import com.otacilio.challange.stylistscheduler.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AppointmentRepository extends CrudRepository<Appointment, Long>, JpaRepository<Appointment, Long> {

}