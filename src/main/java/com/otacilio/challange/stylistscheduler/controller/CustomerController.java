package com.otacilio.challange.stylistscheduler.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.otacilio.challange.stylistscheduler.model.Customer;
import com.otacilio.challange.stylistscheduler.resource.CustomerResourceAssembler;
import com.otacilio.challange.stylistscheduler.service.CustomerService;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

    private final CustomerService service;
    private final CustomerResourceAssembler assembler;

    public CustomerController(CustomerService service, CustomerResourceAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping("/")
    public Resources<Resource<Customer>> all() {
        List<Resource<Customer>> result = service.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(result, linkTo(methodOn(CustomerController.class).all()).withSelfRel());
    }

    @GetMapping("/{id}")
    public Resource<Customer> one(@PathVariable Long id) {
        Customer result = service.find(id);
        return assembler.toResource(result);
    }

    @PostMapping("/")
    public Resource<Customer> create(@Valid @RequestBody Customer customer) {
        Customer result = service.create(customer);
        return assembler.toResource(result);
    }
}
