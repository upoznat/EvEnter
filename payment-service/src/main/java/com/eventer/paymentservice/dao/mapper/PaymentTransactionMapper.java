package com.eventer.paymentservice.dao.mapper;

import com.eventer.paymentservice.domain.PaymentTransaction;
import org.springframework.stereotype.Component;

@Component
public interface PaymentTransactionMapper {

    /**
     * Snima PaymentTransaction
     * @param paymentTransaction
     */
    void savePaymentTransaction(PaymentTransaction paymentTransaction);

    /**
     * Azurira promenljiva polja za PaymentTransaction
     * @param transaction
     * @return - broj azuriranih redova
     */
    int updateTransaction(PaymentTransaction transaction);



}
