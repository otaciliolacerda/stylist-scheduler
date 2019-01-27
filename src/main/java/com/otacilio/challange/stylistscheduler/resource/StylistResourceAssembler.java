package com.otacilio.challange.stylistscheduler.resource;

import com.otacilio.challange.stylistscheduler.controller.CustomerController;
import com.otacilio.challange.stylistscheduler.controller.StylistController;
import com.otacilio.challange.stylistscheduler.model.Customer;
import com.otacilio.challange.stylistscheduler.model.Stylist;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class StylistResourceAssembler implements ResourceAssembler<Stylist, Resource<Stylist>> {

    @Override
	public Resource<Stylist> toResource(Stylist stylist) {
		return new Resource<Stylist>(stylist,
			linkTo(methodOn(StylistController.class).one(stylist.getId())).withSelfRel(),
			linkTo(methodOn(StylistController.class).all()).withRel("stylist"));
	}

}
