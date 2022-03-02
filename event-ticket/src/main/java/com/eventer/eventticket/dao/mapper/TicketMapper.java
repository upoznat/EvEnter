package com.eventer.eventticket.dao.mapper;

import com.eventer.eventticket.domain.Ticket;
import com.eventer.eventticket.domain.Ticket.TicketStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TicketMapper {

    /**
     * Snima ulaznicu
     *
     * @param ticket
     */
    void saveTicket(Ticket ticket);

    /**
     * Azurira podatke o ulaznici
     *
     * @param ticketIds - idjevi dokumenata koji se azuriraju
     */
    void updateTickets(List<Long> ticketIds, Long userId, TicketStatus status);

    /**
     * Azurira podatke o ulaznici
     *  @param eventId id dogadjaja za koji je vezana ulaznica
     * @param numOfTickets broj karata
     * @return
     */
    List<Ticket> getTicketsForEventWithLock(Long eventId, Integer numOfTickets);

}
