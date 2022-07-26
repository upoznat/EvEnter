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
                .name(eventInfo.getName())
                .location(eventInfo.getLocation())
                .address(eventInfo.getAddress())
                .startDate(eventInfo.getStartDate())
                .totalCapacity(eventInfo.getTotalCapacity())
                .availableTickets(eventInfo.getTotalCapacity())
                .status(Active)
                .build();


        eventMapper.saveEvent(event);

        ticketService.createTicketsForEvent(event, eventInfo.getTicketPrice());

        return event;
    }


//    public Event updateEventInfo(EventDTO eventInfo){
//
//        Event event = eventMapper.findEvent(eventInfo.getId());
//
//        Event updatedEvent = Event.builder()
//                .name(eventInfo.getName())
//                .location(eventInfo.getLocation())
//                .address(eventInfo.getAddress())
//                .build();
//
//        if(eventInfo.getTotalCapacity()>(event.getTotalCapacity()){
//            ticketService.createTicketsForEvent(updatedEvent, eventInfo.getTicketPrice());
//        }
//
//        eventMapper.saveEvent(updatedEvent);
//
//        ticketService.createTicketsForEvent(updatedEvent, eventInfo.getTicketPrice());
//
//        return event;
//    }


}
