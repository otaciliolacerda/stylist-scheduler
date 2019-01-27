package com.otacilio.challange.stylistscheduler.controller;

import com.otacilio.challange.stylistscheduler.model.Customer;
import com.otacilio.challange.stylistscheduler.model.TimeSlot;
import com.otacilio.challange.stylistscheduler.resource.CustomerResourceAssembler;
import com.otacilio.challange.stylistscheduler.resource.TimeSlotResourceAssembler;
import com.otacilio.challange.stylistscheduler.service.CustomerService;
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
@RequestMapping("api/v1/timeslot")
public class TimeSlotController {

    private final TimeSlotService service;
    private final TimeSlotResourceAssembler assembler;

    public TimeSlotController(TimeSlotService service, TimeSlotResourceAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping("/")
    public Resources<Resource<TimeSlot>> all() {
        List<Resource<TimeSlot>> result = service.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(result, linkTo(methodOn(TimeSlotController.class).all()).withSelfRel());
    }

    @GetMapping("/{id}")
    public Resource<TimeSlot> one(@PathVariable Long id) {
        TimeSlot result = service.find(id);
        return assembler.toResource(result);
    }

    @PostMapping("/")
    public Resource<TimeSlot> create(@Valid @RequestBody TimeSlot timeSlot) {
        TimeSlot result = service.create(timeSlot);
        return assembler.toResource(result);
    }
}
