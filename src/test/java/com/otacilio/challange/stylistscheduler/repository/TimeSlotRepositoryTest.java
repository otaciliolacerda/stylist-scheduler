package com.otacilio.challange.stylistscheduler.repository;

import com.otacilio.challange.stylistscheduler.model.Appointment;
import com.otacilio.challange.stylistscheduler.model.Customer;
import com.otacilio.challange.stylistscheduler.model.Stylist;
import com.otacilio.challange.stylistscheduler.model.TimeSlot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TimeSlotRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StylistRepository stylistRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    /**
     * The goal here is to understand the CRUDRepository and check if the date filter is working
     */
    @Test
    public void testFindAllByDateBetweenAndAppointmentIsNull() {
        Customer customer1 = customerRepository.save(new Customer("Customer1", "Customer1@gmail.com"));

        Stylist stylist1 = stylistRepository.save(new Stylist("stylist1", "stylist1@gmail.com"));
        Stylist stylist2 = stylistRepository.save(new Stylist("stylist2", "stylist2@gmail.com"));

        TimeSlot slot1 = timeSlotRepository.save(new TimeSlot(stylist1,
                LocalDate.now(),
                LocalTime.of(6, 0),
                LocalTime.of(6, 30)));
        TimeSlot slot2 = timeSlotRepository.save(new TimeSlot(stylist2,
                LocalDate.now(),
                LocalTime.of(6, 0),
                LocalTime.of(6, 30)));

        Appointment appointment1 = appointmentRepository.save(new Appointment(customer1, slot1));

        slot1.setAppointment(appointment1);
        timeSlotRepository.save(slot1);

        List<TimeSlot> available = timeSlotRepository.findAllByDateBetweenAndAppointmentIsNull(LocalDate
                .now().minusDays(1), LocalDate.now().plusDays(2));

        assertNotNull(available);
        assertEquals(1, available.size());
        assertEquals(slot2.getId(), available.get(0).getId());
    }

}