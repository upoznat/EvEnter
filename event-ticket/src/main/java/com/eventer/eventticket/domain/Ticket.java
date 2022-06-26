package com.eventer.eventticket.domain;

import lombok.*;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
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
