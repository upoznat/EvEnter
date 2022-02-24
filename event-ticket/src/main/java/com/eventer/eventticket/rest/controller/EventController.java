package com.eventer.eventticket.rest.controller;


import com.eventer.eventticket.domain.Event;
import com.eventer.eventticket.dto.CreateEventResponse;
import com.eventer.eventticket.dto.EventDTO;
import com.eventer.eventticket.dto.Status;
import com.eventer.eventticket.exception.EventTicketProcessingException;
import com.eventer.eventticket.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class EventController {

    @Autowired
    EventService eventService;

    @PostMapping("createEvent")
    public CreateEventResponse createEvent(@Valid @RequestBody EventDTO newEventRequest){

        try {
            Event event = eventService.persistEventInfo(newEventRequest);
            return CreateEventResponse.builder().status(Status.SUCCESS).eventId(event.getId())
                    .details("").build();

        } catch (EventTicketProcessingException e) {
            return CreateEventResponse
                    .builder()
                    .status(Status.FAIL)
                    .details(e.getErrorType().name())
                    .build();
        }
    }
}
