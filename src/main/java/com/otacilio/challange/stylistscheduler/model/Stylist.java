package com.otacilio.challange.stylistscheduler.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Stylist {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;

    public Stylist() {
    }

    public Stylist(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
