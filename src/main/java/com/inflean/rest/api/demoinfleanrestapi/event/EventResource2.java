package com.inflean.rest.api.demoinfleanrestapi.event;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by hyuns on 2019-02-19.
 */

public class EventResource2 extends Resource<Event> {


    public EventResource2(Event event, Link... links) {
        super(event, links);
        add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
    }
}
