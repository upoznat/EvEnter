package com.eventer.eventticket.service;


import com.eventer.eventticket.amqp.rabbit.RequestSender;
import com.eventer.eventticket.dao.mapper.TicketMapper;
import com.eventer.eventticket.domain.Event;
import com.eventer.eventticket.domain.Ticket;
import com.eventer.eventticket.domain.TicketStatus;
import com.eventer.eventticket.dto.BuyTicketRequest;
import com.eventer.eventticket.dto.TicketWalletResponse;
import com.eventer.eventticket.dto.WalletRequest;
import com.eventer.eventticket.exception.EventTicketProcessingException;
import com.eventer.eventticket.utils.TicketWalletType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.eventer.eventticket.domain.TicketStatus.*;
import static com.eventer.eventticket.domain.TicketStatus.Created;
import static com.eventer.eventticket.domain.TicketStatus.Reserved;
import static com.eventer.eventticket.dto.Status.*;
import static com.eventer.eventticket.exception.ApplicationException.ErrorType.IRREGULAR_RESPONSE;
import static com.eventer.eventticket.exception.ApplicationException.ErrorType.TICKET_RESERVATION_ERROR;

@Service
@Slf4j
public class TicketService {

    @Autowired
    TicketMapper ticketMapper;

    @Autowired
    RequestSender requestSender;

   @Transactional
    public void createTicketsForEvent(Event eventInfo, Double price) {

        //ukoliko nije prosledjena inicijalna cena pri kreiranja eventa, kreiramo samo event bez karata
        if(price==null){
            return;
        }

        IntStream.range(0, eventInfo.getTotalCapacity())
                .forEach(i -> {
                    saveTicket(Ticket.builder()
                            .price(price)
                            .startDate(eventInfo.getStartDate())
                            .event(eventInfo)
                            .status(Created)
                            .build());
                });
    }

    public void saveTicket(Ticket ticket) {
        try {
            ticketMapper.saveTicket(ticket);
        } catch (Exception e) {
            throw new EventTicketProcessingException(e);
        }
    }

    @Transactional
    public List<Long> reserveTickets(BuyTicketRequest request, TicketWalletType reqType) {

        try {
            List<Ticket> ticketsForReserve = ticketMapper.getTicketsForEventWithLock(request.getEventId(), request.getNumberOfTickets());
            List<Long> ticketIds = ticketsForReserve.stream().map(Ticket::getId).collect(Collectors.toList());
            double sum = totalTicketSum(ticketsForReserve);

            //azurirana karta na reserved kako niko drugi ne bi mogao da je kupi dok ne prodje placanje
            ticketMapper.updateTicketsToStatus(ticketIds, request.getCustomerId(), null,  Reserved);

            //poslat zahtev za kupovinom
            WalletRequest walletRequest = WalletRequest.builder()
                    .customerId(request.getCustomerId())
                    .amount(sum)
                    .ticketIds(ticketIds)
                    .type(reqType).build();

            requestSender.sendTicketRequestToWallet(walletRequest);

            return ticketIds;
        } catch (Exception e) {
            throw new EventTicketProcessingException(TICKET_RESERVATION_ERROR);
        }
    }


    @Transactional
    public void resolveReservation(TicketWalletResponse response){
        TicketStatus status=null;

        if (SUCCESS.equals(response.getStatus())) {
            if(TicketWalletType.BUY.equals(response.getType())){
                status = Purchased;
            } else {
                status = Created;
            }
        } else if (FAIL.equals(response.getStatus())) {
            if(TicketWalletType.CANCEL.equals(response.getType())){
                status = Created;
            } else {
                status = Purchased;
            }
            log.info("Neuspela akcija {} karte! Dobijen sledeći odgovor: {}", response.getType(), response);
        } else {
            log.error("Vracen neočekivani odgovor: {}", response);
            throw new EventTicketProcessingException(IRREGULAR_RESPONSE);
        }
        ticketMapper.updateTicketsToStatus(response.getTicketIds(), response.getCustomerId(), response.getDetails(), status);
    }

    private double totalTicketSum(List<Ticket> ticketsForReserve) {
        return ticketsForReserve.stream()
                .map(Ticket::getPrice)
                .reduce(0d, Double::sum)
                .doubleValue();
    }


}
