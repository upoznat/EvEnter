package com.eventer.eventticket.dao.mapper;

import com.eventer.eventticket.domain.Customer;
import com.eventer.eventticket.domain.Event;
import org.springframework.stereotype.Component;

@Component
public interface EventMapper {


    /**
     * Snima podatke o dogadjaju
     *
     * @param event
     */
    void saveEvent(Event event);

    /**
     * Snima podatke o dogadjaju
     *
     * @param event
     */
    void updateEvent(Event event);


    /**
     * Umanjuje broj karata
     *
     * @param number broj karata za koji se azurira broj raspolozivih
     */
    void updateAvailableTicketsFor(int number);


    /**
     * Dohvata dogadjaj po prosledjenom id-u
     * @param id
     * @return
     */
    Customer findEvent(Long id);

}
