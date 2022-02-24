package com.eventer.eventticket.service;


import com.eventer.eventticket.dao.mapper.TicketMapper;
import com.eventer.eventticket.domain.Event;
import com.eventer.eventticket.domain.Ticket;
import com.eventer.eventticket.dto.BuyTicketRequest;
import com.eventer.eventticket.exception.EventTicketProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static com.eventer.eventticket.domain.Ticket.TicketStatus.Created;
import static com.eventer.eventticket.domain.Ticket.TicketStatus.Reserved;

@Service
@Slf4j
public class TicketService {

    @Autowired
    TicketMapper ticketMapper;

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
    public List<Long> reserveTickets(BuyTicketRequest request) {

        try {
            List<Long> ticketsForReserve = ticketMapper.getTicketsForEventWithLock(request.getEventId(), request.getNumberOfTickets());

            ticketMapper.updateTickets(ticketsForReserve, request.getUserId(), Reserved);

            return ticketsForReserve;
        } catch (Exception e) {
            throw new EventTicketProcessingException();
        }
    }


}
