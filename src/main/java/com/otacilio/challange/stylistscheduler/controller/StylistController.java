package com.otacilio.challange.stylistscheduler.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.otacilio.challange.stylistscheduler.model.Stylist;
import com.otacilio.challange.stylistscheduler.resource.StylistResourceAssembler;
import com.otacilio.challange.stylistscheduler.service.StylistService;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/stylist")
public class StylistController {

    private final StylistService service;
    private final StylistResourceAssembler assembler;

    public StylistController(StylistService service, StylistResourceAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping("/")
    public Resources<Resource<Stylist>> all() {
        List<Resource<Stylist>> result = service.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(result, linkTo(methodOn(StylistController.class).all()).withSelfRel());
    }

    @GetMapping("/{id}")
    public Resource<Stylist> one(@PathVariable Long id) {
        Stylist result = service.find(id);
        return assembler.toResource(result);
    }

    @PostMapping("/")
    public Resource<Stylist> create(@Valid @RequestBody Stylist stylist) {
        Stylist result = service.create(stylist);
        return assembler.toResource(result);
    }
}
