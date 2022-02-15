package com.eventer.wallet.mapper;

import com.eventer.wallet.domain.UserTransaction;
import org.springframework.stereotype.Component;

@Component
public interface UserTransactionMapper {

    /**
     *
     * @param transaction
     */
    void save(UserTransaction transaction);

    /**
     * Azurira promenljiva polja za UserTransaction
     * @param transaction
     * @return - broj azuriranih redova
     */
    int update(UserTransaction transaction);

    /**
     *
     * @param trxId
     * @return - vraca transakciju za prosledjeni externalId
     */
    UserTransaction getByExternalId(String trxId);

}
