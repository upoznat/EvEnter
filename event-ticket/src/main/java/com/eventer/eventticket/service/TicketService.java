package com.eventer.eventticket.service;


import com.eventer.eventticket.amqp.rabbit.RequestSender;
import com.eventer.eventticket.dao.mapper.TicketMapper;
import com.eventer.eventticket.domain.Event;
import com.eventer.eventticket.domain.Ticket;
import com.eventer.eventticket.domain.Ticket.TicketStatus;
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

import static com.eventer.eventticket.domain.Ticket.TicketStatus.*;
import static com.eventer.eventticket.domain.Ticket.TicketStatus.Created;
import static com.eventer.eventticket.domain.Ticket.TicketStatus.Reserved;
import static com.eventer.eventticket.dto.Status.*;
import static com.eventer.eventticket.exception.ApplicationException.ErrorType.IRREGULAR_RESPONSE;

@Service
@Slf4j
public class TicketService {

    @Autowired
    TicketMapper ticketMapper;

    @Autowired
    RequestSender requestSender;

    @Transactional
    public void createTicketsForEvent(Event eventInfo, Double price) {

        IntStream.range(0, eventInfo.getTotalCapacity())
                .forEach(i -> {
                    saveTicket(Ticket.builder()
                            .price(price)
                            .startDate(eventInfo.getStartDate())
                            .event(eventInfo)
                            .status(Created).build());
                });
    }

    @Transactional
    public void saveTicket(Ticket ticket) {
        try {
            ticketMapper.saveTicket(ticket);
        } catch (Exception e) {
            throw new EventTicketProcessingException();
        }
    }

    @Transactional
    public List<Long> reserveTickets(BuyTicketRequest request, TicketWalletType reqType) {

        try {
            List<Ticket> ticketsForReserve = ticketMapper.getTicketsForEventWithLock(request.getEventId(), request.getNumberOfTickets());
            List<Long> ticketIds = ticketsForReserve.stream().map(Ticket::getId).collect(Collectors.toList());
            double sum = totalTicketSum(ticketsForReserve);

            //azurirana karta na reserved kako niko drugi ne bi mogao da je kupi dok ne prodje placanje
            ticketMapper.updateTickets(ticketIds, request.getUserId(), Reserved);

            //poslat zahtev za kupovinom
            WalletRequest walletRequest = WalletRequest.builder()
                    .userId(request.getUserId())
                    .amount(sum)
                    .ticketIds(ticketIds)
                    .type(reqType).build();

            requestSender.sendTicketRequestToWallet(walletRequest);

            return ticketIds;
        } catch (Exception e) {
            throw new EventTicketProcessingException();
        }
    }


    @Transactional
    public void resolveReservation(TicketWalletResponse response){
        TicketStatus status=null;

        if (SUCCESS.equals(response.getStatus())) {
            if(TicketWalletType.BUY.equals(response.getType())){
                status = Created;
            } else {
                status = Purchased;
            }
        } else if (FAIL.equals(response.getStatus())) {
            if(TicketWalletType.CANCEL.equals(response.getType())){
                status = Purchased;
            } else {
                status = Created;
            }
            log.info("Neuspela akcija {} karte! Dobijen sledeći odgovor: {}", response.getType(), response);
        } else {
            log.error("Vracen neočekivani odgovor: {}", response);
            throw new EventTicketProcessingException(IRREGULAR_RESPONSE);
        }
        ticketMapper.updateTickets(response.getTicketIds(), response.getUserId(), status);
    }

    private double totalTicketSum(List<Ticket> ticketsForReserve) {
        return ticketsForReserve.stream()
                .map(Ticket::getPrice)
                .reduce(0d, Double::sum)
                .doubleValue();
    }

}
