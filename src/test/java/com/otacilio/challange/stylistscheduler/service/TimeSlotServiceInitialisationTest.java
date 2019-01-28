package com.otacilio.challange.stylistscheduler.service;

import com.otacilio.challange.stylistscheduler.dto.AvailableTimeSlot;
import com.otacilio.challange.stylistscheduler.exception.InvalidDateException;
import com.otacilio.challange.stylistscheduler.exception.InvalidTimeSlotException;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TimeSlotServiceInitialisationTest {

    @Autowired
    private TimeSlotService service;

    @Autowired
    private StylistRepository stylistRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Before
    public void setUp() {
        timeSlotRepository.deleteAll();
        stylistRepository.deleteAll();
    }

    @Test
    public void testInitialiseTimeSlotsNoStylist() {
        List<TimeSlot> slots = service.initialize();
        assertEquals(0, slots.size());
    }

    @Test
    public void testInitialiseTimeSlotsOneStylist() {
        stylistRepository.save(new Stylist("stylist1", "stylist1@gmail.com"));
        List<TimeSlot> slots = service.initialize();
        // 16 slots a day * 60 days
        assertEquals(960, slots.size());
    }

    @Test
    public void testInitialiseTimeSlotsOneStylistWithExistingTimeSlot() {
        Stylist stylist = stylistRepository.save(new Stylist("stylist1", "stylist1@gmail.com"));

        timeSlotRepository.save(new TimeSlot(stylist,
                LocalDate.now().plusDays(2),
                LocalTime.of(9, 0),
                LocalTime.of(9, 30)));

        List<TimeSlot> slots = service.initialize();
        // There is a slot 2 days in the future so the slots to the first 3 days will not be generated.
        // 16 slots a day * 57 days slot
        assertEquals(912, slots.size());
    }

    @Test
    public void testInitialiseTimeSlotsTwoStylists() {
        stylistRepository.save(new Stylist("stylist1", "stylist1@gmail.com"));
        stylistRepository.save(new Stylist("stylist2", "stylist2@gmail.com"));
        List<TimeSlot> slots = service.initialize();
        // 2 stylists * 16 slots a day * 60 days
        assertEquals(1920, slots.size());
    }
}
