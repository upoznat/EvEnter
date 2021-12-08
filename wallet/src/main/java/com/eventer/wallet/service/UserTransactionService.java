package com.eventer.wallet.service;

import com.eventer.wallet.amqp.rabbit.PaymentResponseSender;
import com.eventer.wallet.domain.BalanceType;
import com.eventer.wallet.domain.TransactionStatus;
import com.eventer.wallet.domain.UserTransaction;
import com.eventer.wallet.domain.UserBalance;
import com.eventer.wallet.dto.PaymentResponseDTO;
import com.eventer.wallet.mapper.UserTransactionMapper;
import com.eventer.wallet.mapper.UserBalanceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotSerializeTransactionException;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.eventer.wallet.exception.OptimisticLockingFailureEntityException.lockingFailure;

@Service
@Slf4j
public class UserTransactionService {

    @Autowired
    UserTransactionMapper userTransactionMapper;

    @Autowired
    UserBalanceMapper userBalanceMapper;

    @Transactional
    public PaymentResponseDTO processPaymentRequest(UserTransaction request) {

        PaymentResponseDTO resp = new PaymentResponseDTO(request.getExternalId());

        if (request.getExternalId() != null) {
            UserTransaction etx = userTransactionMapper.getByExternalId(request.getExternalId());
            if (etx != null) {
                return  resp.withStatus(etx.getStatus());
            }
        }

        UserBalance balance = getAvailableBalance(request);
        balance.updateFor(request);

        updateBalance(balance);
        insertTransaction(request);

        return PaymentResponseDTO.builder()
                .id(request.getExternalId())
                .status(TransactionStatus.OK)
                .build();
    }


    private void updateBalance(UserBalance b) {
        try {
            int updated = userBalanceMapper.update(b);
            lockingFailure(updated != 0, "updateBalance", b);
        } catch (DeadlockLoserDataAccessException e) {
            throw lockingFailure("updateBalance", e, b);
        } catch (CannotSerializeTransactionException e) {
            throw lockingFailure("updateBalance", e, b);
        }
    }

    private boolean insertTransaction(UserTransaction tx) {

        //cuvamo i uspesne i neuspesne pokusaje
        userTransactionMapper.save(tx);

        try {
            userTransactionMapper.save(tx);
        } catch (DuplicateKeyException e) {
            throw lockingFailure("insertTransaction", e, tx);
        }

        return true;
    }


    private UserBalance getAvailableBalance(UserTransaction req) {

        UserBalance balance = UserBalance.builder()
                .userId(req.getCustomerId())
                .balanceType(BalanceType.Available)
                .build();

        //sta ako nije kreiran balans???
        UserBalance ub = userBalanceMapper.findBalance(balance);

        if (ub == null) {
            ub = UserBalance.builder().balance(req.getAmount()).build();
            try {
                userBalanceMapper.save(ub);
            } catch (DuplicateKeyException e) {
                throw lockingFailure("getOrCreateBalance", e, ub);
            }

            // da bismo dobili verziju za optimistik lok
            ub = userBalanceMapper.getById(ub.getId());
        }
        return  ub;
    }

}



