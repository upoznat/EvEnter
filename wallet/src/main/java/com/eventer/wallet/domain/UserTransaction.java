package com.eventer.wallet.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class UserTransaction {

    private BigDecimal amount;
    private long customerId;
    private String externalId;
    private TransactionStatus status;

    @Builder
    public UserTransaction(BigDecimal amount, long customerId, String externalId, TransactionStatus status) {
        super();
        this.amount = amount;
        this.customerId = customerId;
        this.externalId = externalId;
        this.status = status;
    }

}
