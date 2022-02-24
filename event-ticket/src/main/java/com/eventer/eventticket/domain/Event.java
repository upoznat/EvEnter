package com.eventer.eventticket.domain;

import com.eventer.eventticket.entitylisteners.EventListener;
import lombok.*;

import javax.persistence.EntityListeners;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
@EntityListeners(EventListener.class)
public class Event {

    private Long id;
    private String name;
    private String location;
    private String address;
    private Instant dateCreated;
    private Instant dateModified;
    private Instant startDate;
    private Integer totalCapacity;
    private Integer availableTickets;
    private EventStatus status;

    public enum EventStatus {
        Active,
        SoldOut,
        Canceled;
    }
}
