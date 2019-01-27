package com.otacilio.challange.stylistscheduler.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.otacilio.challange.stylistscheduler.controller.CustomerController;
import com.otacilio.challange.stylistscheduler.model.Customer;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class CustomerResourceAssembler implements ResourceAssembler<Customer, Resource<Customer>> {

    @Override
	public Resource<Customer> toResource(Customer customer) {
		return new Resource<Customer>(customer,
			linkTo(methodOn(CustomerController.class).one(customer.getId())).withSelfRel(),
			linkTo(methodOn(CustomerController.class).all()).withRel("customer"));
	}

}
