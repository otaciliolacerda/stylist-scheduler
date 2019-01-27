package com.otacilio.challange.stylistscheduler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@ToString(exclude = "appointment")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"stylist_id", "date", "start", "end"})})
public class TimeSlot {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "stylist_id", nullable = false)
    private Stylist stylist;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    @JsonIgnore
    private Appointment appointment;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime start;

    @NotNull
    private LocalTime end;

    public TimeSlot() {
    }

    public TimeSlot(Stylist stylist, LocalDate date, LocalTime start, LocalTime end) {
        this.stylist = stylist;
        this.date = date;
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TimeSlot)) {
            return false;
        }
        TimeSlot cc = (TimeSlot) o;
        return cc.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode() {
        return (date.toString() + start.toString() + end.toString()).hashCode();
    }
}
