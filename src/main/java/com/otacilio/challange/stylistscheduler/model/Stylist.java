package com.otacilio.challange.stylistscheduler.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Entity
public class Stylist {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Column(nullable = false, unique = true)
    @NotBlank
    @Email(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
    private String email;

    private LocalDate createdOn = LocalDate.now();

    public Stylist() {
    }

    public Stylist(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
