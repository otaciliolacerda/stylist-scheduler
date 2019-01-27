package com.otacilio.challange.stylistscheduler.service;

import com.otacilio.challange.stylistscheduler.model.Appointment;
import com.otacilio.challange.stylistscheduler.model.Customer;
import com.otacilio.challange.stylistscheduler.model.Stylist;
import com.otacilio.challange.stylistscheduler.model.TimeSlot;
import com.otacilio.challange.stylistscheduler.repository.CustomerRepository;
import com.otacilio.challange.stylistscheduler.repository.StylistRepository;
import com.otacilio.challange.stylistscheduler.repository.TimeSlotRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppointmentServiceTest {

    @Autowired
    private AppointmentService service;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StylistRepository stylistRepository;

    @Before
    public void setUp() {
        timeSlotRepository.deleteAll();

        customerRepository.save(new Customer("Customer1", "Customer1@gmail.com"));
        customerRepository.save(new Customer("Customer2", "Customer2@gmail.com"));

        Stylist stylist1 = stylistRepository.save(new Stylist("stylist1", "stylist1@gmail.com"));
        Stylist stylist2 = stylistRepository.save(new Stylist("stylist2", "stylist2@gmail.com"));

        timeSlotRepository.save(new TimeSlot(stylist1,
                LocalDate.now().plusDays(1),
                LocalTime.of(6, 30),
                LocalTime.of(7, 0)));
        timeSlotRepository.save(new TimeSlot(stylist2,
                LocalDate.now().plusDays(1),
                LocalTime.of(6, 30),
                LocalTime.of(7, 0)));
        timeSlotRepository.save(new TimeSlot(stylist1,
                LocalDate.now(),
                LocalTime.of(6, 0),
                LocalTime.of(6, 30)));
    }

    @Test
    public void testGetAllAvailableTimeSlots() {
        List<TimeSlot> available = timeSlotRepository.findAllByDateBetweenAndAppointmentIsNull(LocalDate
                .now().minusDays(1), LocalDate.now().plusDays(2));
        // Two time slots are overlaping so just 2 are expected
        assertEquals(2, available.size());

    }
}
