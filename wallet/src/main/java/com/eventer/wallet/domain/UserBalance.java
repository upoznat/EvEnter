package com.eventer.wallet.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

import static java.math.BigDecimal.ZERO;

@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class UserBalance {

        Long id;
        Instant dateCreated;
        Instant dateModified;
        Long userId;
        BalanceType balanceType;
        BigDecimal balance;


    @Builder
    public UserBalance(Long id, Instant dateCreated, Instant dateModified, Long userId, BalanceType balanceType, BigDecimal balance) {
        super();
        this.id = id;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.userId = userId;
        this.balanceType = balanceType;
        this.balance = balance;
    }

    public void updateFor(UserTransaction request) {

        if (balance == null) {
            balance = ZERO;
        }

        this.balance = balance.add(request.getAmount());
        request.setStatus(TransactionStatus.OK);
    }

}
