package com.otacilio.challange.stylistscheduler.controller;

import com.otacilio.challange.stylistscheduler.exception.InvalidDateException;
import com.otacilio.challange.stylistscheduler.exception.InvalidTimeSlotException;
import com.otacilio.challange.stylistscheduler.dto.AvailableTimeSlot;
import com.otacilio.challange.stylistscheduler.model.TimeSlot;
import com.otacilio.challange.stylistscheduler.resource.TimeSlotResourceAssembler;
import com.otacilio.challange.stylistscheduler.service.TimeSlotService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
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
    public Resource<TimeSlot> create(@Valid @RequestBody TimeSlot timeSlot) throws InvalidTimeSlotException,
            InvalidDateException {
        TimeSlot result = service.create(timeSlot);
        return assembler.toResource(result);
    }

    @GetMapping("/available/{date}")
    public List<AvailableTimeSlot> available(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                     LocalDate date) throws InvalidDateException {
        return service.findAvailable(date);
    }

    @PostMapping("/initialise")
    public Resources<Resource<TimeSlot>> initialize() {
        List<Resource<TimeSlot>> result = service.initialize().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(result, linkTo(methodOn(TimeSlotController.class).all()).withSelfRel());
    }
}
