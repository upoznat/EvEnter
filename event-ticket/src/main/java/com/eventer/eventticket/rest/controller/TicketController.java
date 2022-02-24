package com.eventer.eventticket.rest.controller;

import com.eventer.eventticket.domain.Event;
import com.eventer.eventticket.domain.Ticket;
import com.eventer.eventticket.dto.*;
import com.eventer.eventticket.exception.EventTicketProcessingException;
import com.eventer.eventticket.service.EventService;
import com.eventer.eventticket.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class TicketController {

    @Autowired
    TicketService ticketService;

    @PostMapping("reserveTicket")
    public BuyTicketResponse buyTicket(@Valid @RequestBody BuyTicketRequest request){

        try {
            //rezervisane karte
            List<Long> tickets = ticketService.reserveTickets(request);

            //sad treba da se salje zahtev za placanjem sa kjua

            return BuyTicketResponse.builder()
                    .status(Status.SUCCESS)
                    .ticketIds(tickets)
                    .details("").build();

        } catch (EventTicketProcessingException e) {
            return BuyTicketResponse
                    .builder()
                    .status(Status.FAIL)
                    .details(e.getErrorType().name())
                    .build();
        }
    }
}
