package com.eventer.eventticket.domain;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class Event {

    private Long id;
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
