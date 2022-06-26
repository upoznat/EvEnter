package com.eventer.eventticket.service;

import com.eventer.eventticket.dao.mapper.EventMapper;
import com.eventer.eventticket.domain.Event;
import com.eventer.eventticket.dto.EventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.eventer.eventticket.domain.EventStatus.Active;

@Service
@Slf4j
public class EventService {

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private TicketService ticketService;

    @Transactional
    public Event persistEventInfo(EventDTO eventInfo){

        Event event = Event.builder()
                .location(eventInfo.getLocation())
                .address(eventInfo.getAddress())
                .totalCapacity(eventInfo.getTotalCapacity())
                .availableTickets(eventInfo.getTotalCapacity())
                .name(eventInfo.getName())
                .status(Active).build();


        eventMapper.saveEvent(event);

        ticketService.createTicketsForEvent(event, eventInfo.getTicketPrice());

        return event;
    }
}
