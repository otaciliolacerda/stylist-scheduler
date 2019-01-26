package com.otacilio.challange.stylistscheduler.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@ToString(exclude = "appointment")
public class TimeSlot {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "stylist_id", nullable = false)
    private Stylist stylist;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    private LocalDate date;
    private LocalTime start;
    private LocalTime end;

    public TimeSlot() {
    }

    public TimeSlot(Stylist stylist, LocalDate date, LocalTime start, LocalTime end) {
        this.stylist = stylist;
        this.date = date;
        this.start = start;
        this.end = end;
    }

}
