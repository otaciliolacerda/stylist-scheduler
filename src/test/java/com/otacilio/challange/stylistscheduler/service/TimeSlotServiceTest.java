package com.otacilio.challange.stylistscheduler.service;

import com.otacilio.challange.stylistscheduler.exception.InvalidTimeSlotException;
import com.otacilio.challange.stylistscheduler.exception.InvalidDateException;
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
public class TimeSlotServiceTest {

    @Autowired
    private TimeSlotService service;

    @Autowired
    private StylistRepository stylistRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Stylist stylist;


    @Before
    public void setUp() {
        timeSlotRepository.deleteAll();
        stylistRepository.deleteAll();

        stylist = stylistRepository.save(new Stylist("stylist1", "stylist1@gmail.com"));
        Stylist stylist2 = stylistRepository.save(new Stylist("stylist2", "stylist2@gmail.com"));

        timeSlotRepository.save(new TimeSlot(stylist,
                LocalDate.now().plusDays(1),
                LocalTime.of(6, 30),
                LocalTime.of(7, 0)));
        timeSlotRepository.save(new TimeSlot(stylist2,
                LocalDate.now().plusDays(1),
                LocalTime.of(6, 30),
                LocalTime.of(7, 0)));
        timeSlotRepository.save(new TimeSlot(stylist,
                LocalDate.now().plusDays(1),
                LocalTime.of(6, 0),
                LocalTime.of(6, 30)));
    }

    @Test
    public void testAddTimeSlotToStylist() throws InvalidTimeSlotException, InvalidDateException {
        TimeSlot timeSlot = service.create(new TimeSlot(stylist,
                LocalDate.now().plusDays(2), // Different day
                LocalTime.of(6, 0),
                LocalTime.of(6, 30)));
        assertNotNull(timeSlot);
    }

    @Test(expected = InvalidTimeSlotException.class)
    public void testAddDuplicatedTimeSlotToStylist() throws InvalidTimeSlotException, InvalidDateException {
        service.create(new TimeSlot(stylist,
                LocalDate.now().plusDays(1),
                LocalTime.of(6, 0),
                LocalTime.of(6, 30)));
    }

    @Test(expected = InvalidDateException.class)
    public void testAddTimeSlotWithPastDate() throws InvalidTimeSlotException, InvalidDateException {
        service.create(new TimeSlot(stylist,
                LocalDate.now().minusDays(1),
                LocalTime.of(6, 0),
                LocalTime.of(6, 30)));
    }

    @Test()
    public void testGetAllAvailableTimeSlots() throws InvalidTimeSlotException, InvalidDateException {
        List<TimeSlot> available = service.findAvailable(LocalDate.now());
        assertEquals(2, available.size()); // There are 2 overlapping time slots so just 2 are expected
    }

    @Test(expected = InvalidDateException.class)
    public void testGetAvailableTimeSlotsPastDate() throws InvalidDateException {
        service.findAvailable(LocalDate.now().minusDays(1));
    }

    @Test(expected = InvalidDateException.class)
    public void testGetAvailableTimeSlotsFutureDate() throws InvalidDateException {
        service.findAvailable(LocalDate.now().plusDays(31));
    }
}
