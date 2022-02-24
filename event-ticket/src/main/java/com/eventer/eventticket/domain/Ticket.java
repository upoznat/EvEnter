package com.eventer.eventticket.domain;

import lombok.*;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class Ticket {

    private Long id;
    private Instant dateCreated;
    private Instant dateModified;
    private Instant startDate;
    private Double price;
    private Event event;
    private User user;
    private TicketStatus status;
    private String transactionDetails;


public enum TicketStatus {
    Created,
    Reserved,
    Purchased,
    Deleted;
}

    @PrePersist
    public void prePersist() {
        dateCreated = Instant.now();
        dateModified = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        dateModified = Instant.now();
    }

}
