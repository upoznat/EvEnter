package com.eventer.paymentservice.domain;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class PaymentTransaction {

    private Long id;
    private Instant dateCreated;
    private Instant dateModified;
    private User user;
    private PaymentType paymentType;
    private String provider;
    private PaymentStatus paymentStatus;
    private String uuid;


    @Builder
    public PaymentTransaction(Long id, Instant dateCreated, Instant dateModified, User user,
                              PaymentType paymentType, String provider, PaymentStatus paymentStatus, String uuid) {
        super();
        this.id = id;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.user = user;
        this.paymentType = paymentType;
        this.provider = provider;
        this.paymentStatus = paymentStatus;
        this.uuid = uuid;
    }

}
