package com.inflean.rest.api.demoinfleanrestapi.event;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

/**
 * Created by hyuns on 2019-02-19.
 */
@Component
public class EventValidator {
    public void validate(EventDto eventDto, Errors errors){
        if(eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() > 0){
            errors.rejectValue("basePrice","wrongValud","BasePrice is wrong");
            errors.rejectValue("maxPrice","maxPrice","MaxPrice is wrong");
            errors.reject("maxPrice","MaxPrice is wrong");
        }
        LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
        if(endEventDateTime.isBefore(eventDto.getBeginEventDateTime())||
            endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
                endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())){
            errors.rejectValue("endEventDateTime","wrong value","EndEventTimeDateTime");
        }


    }
}
