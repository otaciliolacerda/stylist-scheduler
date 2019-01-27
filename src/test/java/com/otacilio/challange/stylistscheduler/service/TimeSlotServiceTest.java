package com.otacilio.challange.stylistscheduler.service;

import com.otacilio.challange.stylistscheduler.exception.InvalidTimeSlotException;
import com.otacilio.challange.stylistscheduler.exception.InvalidDateException;
import com.otacilio.challange.stylistscheduler.model.Stylist;
import com.otacilio.challange.stylistscheduler.model.TimeSlot;
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
public class TimeSlotServiceTest {

    @Autowired
    private TimeSlotService service;

    @Autowired
    private StylistRepository stylistRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    private Stylist stylist;


    @Before
    public void setUp() {
        timeSlotRepository.deleteAll();
        stylistRepository.deleteAll();
        stylist = stylistRepository.save(new Stylist("stylist1", "stylist1@email.com"));
    }

    @Test
    public void testAddTimeSlotToStylist() throws InvalidTimeSlotException, InvalidDateException {
        TimeSlot timeSlot = service.create(new TimeSlot(stylist,
                LocalDate.now().plusDays(1),
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
}
