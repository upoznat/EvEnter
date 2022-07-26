package com.eventer.eventticket.rest.controller;

import com.eventer.eventticket.dto.*;
import com.eventer.eventticket.exception.EventTicketProcessingException;
import com.eventer.eventticket.service.TicketService;
import com.eventer.eventticket.utils.TicketWalletType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.ws.rs.Path;
import java.util.List;

import static com.eventer.eventticket.utils.TicketWalletType.BUY;
import static com.eventer.eventticket.utils.TicketWalletType.CANCEL;

@Slf4j
@RequestMapping("/ticket")
@RestController
public class TicketController {

    @Autowired
    TicketService ticketService;

    @PostMapping("reserve")
    public CustomResponse buyTicket(@Valid @RequestBody BuyTicketRequest request){

        try {
            //rezervisane karte
            List<Long> tickets = ticketService.reserveTickets(request, BUY);

            return CustomResponse.builder()
                    .status(Status.SUCCESS)
                    .details("Rezervisane karte sa sledecim idjevima: "+ tickets)
                    .build();

        } catch (EventTicketProcessingException e) {
            return CustomResponse
                    .builder()
                    .status(Status.FAIL)
                    .details(e.getErrorType().getDescription())
                    .build();
        }
    }

    @PostMapping("cancelTicket")
    public CustomResponse cancelTicket(@Valid @RequestBody BuyTicketRequest request){

        try {
            //rezervisane karte
            List<Long> tickets = ticketService.reserveTickets(request, CANCEL);

            return CustomResponse.builder()
                    .status(Status.SUCCESS)
                    .details("Otkazane su karte sa sledecim idjevima: "+ tickets)
                    .build();

        } catch (EventTicketProcessingException e) {
            return CustomResponse
                    .builder()
                    .status(Status.FAIL)
                    .details(e.getErrorType().getDescription())
                    .build();
        }
    }
}
