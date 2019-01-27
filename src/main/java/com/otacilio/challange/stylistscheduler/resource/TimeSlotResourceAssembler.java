package com.otacilio.challange.stylistscheduler.resource;

import com.otacilio.challange.stylistscheduler.controller.TimeSlotController;
import com.otacilio.challange.stylistscheduler.model.TimeSlot;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class TimeSlotResourceAssembler implements ResourceAssembler<TimeSlot, Resource<TimeSlot>> {

    @Override
	public Resource<TimeSlot> toResource(TimeSlot timeSlot) {
		return new Resource<TimeSlot>(timeSlot,
			linkTo(methodOn(TimeSlotController.class).one(timeSlot.getId())).withSelfRel(),
			linkTo(methodOn(TimeSlotController.class).all()).withRel("timeslot"));
	}

}
