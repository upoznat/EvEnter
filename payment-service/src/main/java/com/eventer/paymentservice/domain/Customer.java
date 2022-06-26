package com.eventer.paymentservice.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class Customer {

    private Long id;

    private String username;

    private String identityNumber;


    @Builder
    public Customer(Long id, String username, String identityNumber) {
        super();
        this.id = id;
        this.username = username;
        this.identityNumber = identityNumber;
    }

}
