package com.otacilio.challange.stylistscheduler.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "APPOINTMENT")
public class Appointment {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;

    private LocalDate createOn = LocalDate.now();

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "timeslot_id", nullable = false)
    @NotNull
    private TimeSlot timeSlot;

    public Appointment() {
    }

    public Appointment(Customer customer, TimeSlot timeSlot) {
        this.customer = customer;
        this.timeSlot = timeSlot;
    }

}
