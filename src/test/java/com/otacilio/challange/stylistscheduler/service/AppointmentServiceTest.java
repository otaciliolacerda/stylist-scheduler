package com.otacilio.challange.stylistscheduler.service;

import com.otacilio.challange.stylistscheduler.dto.AvailableTimeSlot;
import com.otacilio.challange.stylistscheduler.exception.InvalidAppointmentException;
import com.otacilio.challange.stylistscheduler.exception.TimeSlotNotAvailableException;
import com.otacilio.challange.stylistscheduler.model.Appointment;
import com.otacilio.challange.stylistscheduler.model.Customer;
import com.otacilio.challange.stylistscheduler.model.Stylist;
import com.otacilio.challange.stylistscheduler.model.TimeSlot;
import com.otacilio.challange.stylistscheduler.repository.AppointmentRepository;
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

    @Autowired
    private AppointmentRepository appointmentRepository;

    private Customer customer1;
    private TimeSlot timeSlot1;

    @Before
    public void setUp() {
        appointmentRepository.deleteAll();
        timeSlotRepository.deleteAll();
        customerRepository.deleteAll();
        stylistRepository.deleteAll();

        customer1 = customerRepository.save(new Customer("customer1", "customer1@gmail.com"));
        customerRepository.save(new Customer("Customer2", "Customer2@gmail.com"));

        Stylist stylist1 = stylistRepository.save(new Stylist("stylist1", "stylist1@gmail.com"));
        Stylist stylist2 = stylistRepository.save(new Stylist("stylist2", "stylist2@gmail.com"));

        timeSlot1 = timeSlotRepository.save(new TimeSlot(stylist1,
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
    public void testMakeAnAppointment() throws InvalidAppointmentException, TimeSlotNotAvailableException {
        AvailableTimeSlot slot = new AvailableTimeSlot(LocalDate.now().plusDays(1),
                LocalTime.of(6, 30), LocalTime.of(7, 0));
        Appointment ap = service.create(customer1, slot);
        assertNotNull(ap);
        assertNotNull(ap.getTimeSlot());

    }

    @Test(expected = InvalidAppointmentException.class)
    public void testMakeAppointmentInvalidCustomer() throws InvalidAppointmentException, TimeSlotNotAvailableException {
        AvailableTimeSlot slot = new AvailableTimeSlot(LocalDate.now().plusDays(1),
                LocalTime.of(6, 30), LocalTime.of(7, 0));
        Appointment ap = service.create(new Customer("invalid", "invalid@email.com"), slot);
        assertNotNull(ap);
        assertNotNull(ap.getTimeSlot());

    }

    @Test(expected = TimeSlotNotAvailableException.class)
    public void testMakeAppointmentTimeSlotNotAvailable() throws InvalidAppointmentException, TimeSlotNotAvailableException {
        AvailableTimeSlot slot = new AvailableTimeSlot(LocalDate.now(), LocalTime.of(6, 30), LocalTime.of(7, 0));
        Appointment ap = service.create(customer1, slot);
        assertNotNull(ap);
        assertNotNull(ap.getTimeSlot());

    }

}
