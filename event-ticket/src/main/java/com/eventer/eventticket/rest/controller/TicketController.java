package com.eventer.eventticket.rest.controller;

import com.eventer.eventticket.dto.*;
import com.eventer.eventticket.exception.EventTicketProcessingException;
import com.eventer.eventticket.service.TicketService;
import com.eventer.eventticket.utils.TicketWalletType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.eventer.eventticket.utils.TicketWalletType.BUY;
import static com.eventer.eventticket.utils.TicketWalletType.CANCEL;

@Slf4j
@RestController
public class TicketController {

    @Autowired
    TicketService ticketService;

    @PostMapping("reserveTicket")
    public TicketWalletResponse buyTicket(@Valid @RequestBody BuyTicketRequest request){

        try {
            //rezervisane karte
            List<Long> tickets = ticketService.reserveTickets(request, BUY);

            return TicketWalletResponse.builder()
                    .status(Status.SUCCESS)
                    .ticketIds(tickets)
                    .details("").build();

        } catch (EventTicketProcessingException e) {
            return TicketWalletResponse
                    .builder()
                    .status(Status.FAIL)
                    .details(e.getErrorType().name())
                    .build();
        }
    }

    @PostMapping("cancelTicket")
    public TicketWalletResponse cancelTicket(@Valid @RequestBody BuyTicketRequest request){

        try {
            //rezervisane karte
            List<Long> tickets = ticketService.reserveTickets(request, CANCEL);

            return TicketWalletResponse.builder()
                    .status(Status.SUCCESS)
                    .ticketIds(tickets)
                    .details("").build();

        } catch (EventTicketProcessingException e) {
            return TicketWalletResponse
                    .builder()
                    .status(Status.FAIL)
                    .details(e.getErrorType().name())
                    .build();
        }
    }
}
