package com.eventer.eventticket.dao.mapper;

import com.eventer.eventticket.domain.Ticket;
import com.eventer.eventticket.domain.TicketStatus;
import org.apache.ibatis.annotations.Param;
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
    void updateTicketsToStatus(@Param("ticketIds")List<Long> ticketIds, @Param("customerId") Long customerId, @Param("details")String details, @Param("status") TicketStatus status);

    /**
     * Dohvata broj karata ulaznica za dogadjaj
     *  @param eventId id dogadjaja za koji je vezana ulaznica
     * @param numOfTickets broj karata
     * @return
     */
    List<Ticket> getTicketsForEventWithLock(@Param("eventId") Long eventId, @Param("numOfTickets") Integer numOfTickets);

}
