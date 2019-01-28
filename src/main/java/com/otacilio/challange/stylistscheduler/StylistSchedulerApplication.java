package com.otacilio.challange.stylistscheduler;

import com.otacilio.challange.stylistscheduler.model.*;
import com.otacilio.challange.stylistscheduler.repository.AppointmentRepository;
import com.otacilio.challange.stylistscheduler.repository.CustomerRepository;
import com.otacilio.challange.stylistscheduler.repository.StylistRepository;
import com.otacilio.challange.stylistscheduler.repository.TimeSlotRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Configuration
@Slf4j
@SpringBootApplication
public class StylistSchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StylistSchedulerApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner initDatabase(CustomerRepository customerRepository, StylistRepository
//            stylistRepository, TimeSlotRepository timeSlotRepository, AppointmentRepository appointmentRepository) {
//        return args -> {
//            Customer customer1 = customerRepository.save(new Customer("Customer1", "Customer1@gmail.com"));
//            Customer customer2 = customerRepository.save(new Customer("Customer2", "Customer2@gmail.com"));
//            log.info("CUSTOMER " + customer1);
//            log.info("CUSTOMER " + customer2);
//
//            Stylist stylist1 = stylistRepository.save(new Stylist("stylist1", "stylist1@gmail.com"));
//            Stylist stylist2 = stylistRepository.save(new Stylist("stylist2", "stylist2@gmail.com"));
//            log.info("STYLIST " + stylist1);
//            log.info("STYLIST " + stylist2);
//
//            // Should return just one available since they cover the same time frame
//            timeSlotRepository.save(new TimeSlot(stylist1,
//                    LocalDate.now(),
//                    LocalTime.of(6, 30),
//                    LocalTime.of(7, 0)));
//            timeSlotRepository.save(new TimeSlot(stylist2,
//                    LocalDate.now(),
//                    LocalTime.of(6, 30),
//                    LocalTime.of(7, 0)));
//
//            TimeSlot slot1 = timeSlotRepository.save(
//                    new TimeSlot(stylist1, LocalDate.now(), LocalTime.of(6, 0),
//                            LocalTime.of(6, 30)));
//            log.info("TIME SLOT " + slot1);
//            log.info("TIME SLOT " + timeSlotRepository.save(new TimeSlot(stylist2, LocalDate.now(), LocalTime.of(6, 0),
//                    LocalTime.of(6, 30))));
//
//            Appointment appointment1 = appointmentRepository.save(new Appointment(customer1, slot1));
//            log.info("APPOINTMENT " + appointment1);
//
//            slot1.setAppointment(appointment1);
//            log.info("UPDATE TIME SLOT " + timeSlotRepository.save(slot1));
//
//            List<TimeSlot> available = timeSlotRepository.findAllByDateBetweenAndAppointmentIsNull(LocalDate
//                    .now().minusDays(1), LocalDate.now().plusDays(2));
//            log.info("AVAILABLE " + available);
//        };
//    }

}
