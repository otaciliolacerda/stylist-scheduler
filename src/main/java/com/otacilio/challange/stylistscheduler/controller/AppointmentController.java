package com.otacilio.challange.stylistscheduler.controller;

import com.otacilio.challange.stylistscheduler.model.Appointment;
import com.otacilio.challange.stylistscheduler.model.TimeSlot;
import com.otacilio.challange.stylistscheduler.resource.AppointmentResourceAssembler;
import com.otacilio.challange.stylistscheduler.resource.TimeSlotResourceAssembler;
import com.otacilio.challange.stylistscheduler.service.AppointmentService;
import com.otacilio.challange.stylistscheduler.service.TimeSlotService;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("api/v1/appointment")
public class AppointmentController {

    private final AppointmentService service;
    private final AppointmentResourceAssembler assembler;

    public AppointmentController(AppointmentService service, AppointmentResourceAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping("/")
    public Resources<Resource<Appointment>> all() {
        List<Resource<Appointment>> result = service.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(result, linkTo(methodOn(AppointmentController.class).all()).withSelfRel());
    }

    @GetMapping("/{id}")
    public Resource<Appointment> one(@PathVariable Long id) {
        Appointment result = service.find(id);
        return assembler.toResource(result);
    }

    @PostMapping("/")
    public Resource<Appointment> create(@Valid @RequestBody Appointment appointment) {
        Appointment result = service.create(appointment);
        return assembler.toResource(result);
    }
}
