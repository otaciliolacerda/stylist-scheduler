package com.otacilio.challange.stylistscheduler.resource;

import com.otacilio.challange.stylistscheduler.controller.AppointmentController;
import com.otacilio.challange.stylistscheduler.model.Appointment;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class AppointmentResourceAssembler implements ResourceAssembler<Appointment, Resource<Appointment>> {

    @Override
	public Resource<Appointment> toResource(Appointment appointment) {
		return new Resource<Appointment>(appointment,
			linkTo(methodOn(AppointmentController.class).one(appointment.getId())).withSelfRel(),
			linkTo(methodOn(AppointmentController.class).all()).withRel("appointment"));
	}

}
