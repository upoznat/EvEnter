package com.eventer.eventticket.entitylisteners;

import com.eventer.eventticket.dao.mapper.EventMapper;
import com.eventer.eventticket.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;

@Component
public class EventListener {

    @Autowired
    EventMapper mapper;

    @PrePersist
    public void prePersist(Event event) {
        event.setDateCreated(Instant.now());
        event.setDateModified(Instant.now());
    }

    @PreUpdate
    public void preUpdate(Event event) {
        event.setDateModified(Instant.now());
    }

    @PostUpdate
    public void postUpdate(Event event) {
        if (event.getAvailableTickets().equals(0)) {
            event.setStatus(Event.EventStatus.SoldOut);
            mapper.updateEvent(event);
        }
    }
}
