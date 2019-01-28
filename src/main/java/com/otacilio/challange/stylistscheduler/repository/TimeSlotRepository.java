package com.otacilio.challange.stylistscheduler.repository;

import com.otacilio.challange.stylistscheduler.model.TimeSlot;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TimeSlotRepository extends CrudRepository<TimeSlot, Long>, JpaRepository<TimeSlot, Long> {

    List<TimeSlot> findAllByDateBetweenAndAppointmentIsNull(LocalDate startDate, LocalDate endDate);

    List<TimeSlot> findAllByDateEqualsAndStartEqualsAndEndEquals(LocalDate date, LocalTime start, LocalTime end);

    TimeSlot findFirstByOrderByDateDesc();
}