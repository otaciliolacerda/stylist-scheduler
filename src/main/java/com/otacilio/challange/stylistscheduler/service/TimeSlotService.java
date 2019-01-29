package com.otacilio.challange.stylistscheduler.service;

import com.otacilio.challange.stylistscheduler.exception.InvalidDateException;
import com.otacilio.challange.stylistscheduler.exception.InvalidTimeSlotException;
import com.otacilio.challange.stylistscheduler.exception.ResourceNotFoundException;
import com.otacilio.challange.stylistscheduler.dto.AvailableTimeSlot;
import com.otacilio.challange.stylistscheduler.model.Stylist;
import com.otacilio.challange.stylistscheduler.model.TimeSlot;
import com.otacilio.challange.stylistscheduler.repository.TimeSlotRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeSlotService {

    private TimeSlotRepository repository;
    private StylistService stylistService;

    public TimeSlotService(TimeSlotRepository repository, StylistService stylistService) {
        this.repository = repository;
        this.stylistService = stylistService;
    }

    public TimeSlot find(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
    }

    public List<TimeSlot> findAll() {
        return repository.findAll();
    }

    public TimeSlot create(TimeSlot timeSlot) throws InvalidTimeSlotException, InvalidDateException {
        if (timeSlot.getDate().isBefore(LocalDate.now()) ||
                (timeSlot.getDate().isEqual(LocalDate.now()) && timeSlot.getStart().isBefore(LocalTime.now()))) {
            throw new InvalidDateException("It is not possible to create time slots for past dates");
        }

        try {
            return repository.save(timeSlot);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidTimeSlotException("Invalid TimeSlot");
        }
    }

    /**
     * Get all available time slots in the next 7 days starting from 'date'. Past dates or dates more than 30 days
     * in the future are invalid
     *
     * @param date Starting date
     * @return All available time slots in the time frame
     */
    public List<AvailableTimeSlot> findAvailable(LocalDate date) throws InvalidDateException {
        if (date.isBefore(LocalDate.now()) || date.isAfter(LocalDate.now().plusDays(30))) {
            throw new InvalidDateException("Invalid date");
        }
        List<TimeSlot> available = repository.findAllByDateBetweenAndAppointmentIsNull(date, date.plusDays(6))
                .stream().distinct().collect(Collectors.toList());
        return available.stream().map(a -> new AvailableTimeSlot(a.getDate(), a.getStart(), a.getEnd())).collect(Collectors.toList());
    }

    /**
     * Find all TimeSlots for the giver date, start and end
     *
     * @param date  Date
     * @param start Starting HH:MM
     * @param end   Ending HH:MM
     * @return List of TimeSlots
     */
    public List<TimeSlot> findAll(LocalDate date, LocalTime start, LocalTime end) {
        return repository.findAllByDateEqualsAndStartEqualsAndEndEquals(date, start, end);
    }


    /**
     * Generate all missing time slots for a period of 60 days starting from the current date. The starting time is
     * hardcode to 09:00h and the end time to 18:00h (16slots/stylist/day). All slots have the same length of 30
     * minutes. First checks for the oldest slot and assumes the day is full. Starts to initialise the slots
     * starting from the day after
     *
     * @return List of TimeSlots
     */
    @Transactional // Avoid partial creation
    public List<TimeSlot> initialize() {

        // Get oldest slot
        TimeSlot oldest = repository.findFirstByOrderByDateDesc();
        LocalDate startDate = LocalDate.now();

        long initialIncrement = 0;
        if (oldest != null && oldest.getDate().isAfter(startDate)) {
            initialIncrement = startDate.until(oldest.getDate(), ChronoUnit.DAYS) + 1; // Day after the date found
        }

        List<TimeSlot> slots = new LinkedList<TimeSlot>();

        for (Stylist stylist : stylistService.findAll()) {
            for (long dayIncrement = initialIncrement; dayIncrement < 60; dayIncrement++) {

                LocalTime currentTime = LocalTime.of(9,0);

                for (int slotNumber = 0; slotNumber < 16; slotNumber++) {
                    slots.add(new TimeSlot(
                            stylist,
                            startDate.plusDays(dayIncrement),
                            currentTime,
                            currentTime.plusMinutes(30)
                    ));
                    currentTime = currentTime.plusMinutes(30);
                }
            }
        }
        return repository.saveAll(slots);
    }
}
