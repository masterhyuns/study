package com.inflean.rest.api.demoinfleanrestapi.event;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by hyuns on 2019-02-19.
 */

public class EventResource extends ResourceSupport {
    @JsonUnwrapped
    private Event event;

    public EventResource(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }
}
