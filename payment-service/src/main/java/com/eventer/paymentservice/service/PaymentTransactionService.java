package com.eventer.paymentservice.service;

import com.eventer.paymentservice.dao.mapper.PaymentTransactionMapper;
import com.eventer.paymentservice.dao.mapper.UserMapper;
import com.eventer.paymentservice.domain.PaymentStatus;
import com.eventer.paymentservice.domain.PaymentTransaction;
import com.eventer.paymentservice.domain.PaymentType;
import com.eventer.paymentservice.domain.User;
import com.eventer.paymentservice.dto.ExternalPaymentRequestDTO;
import com.eventer.paymentservice.dto.PaymentResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.regex.Pattern;

@Service
@Slf4j
public class PaymentTransactionService {

    @Autowired
    PaymentTransactionMapper paymentTransactionMapper;

    @Autowired
    UserMapper userMapper;


    /**
     * Pronalazi korisnika za zadati JMBG i snima zahtev za uplatu u pocetnom statusu ili u statusu greske ako korisnik nije pronadjen
     */
    @Transactional
    public void createAndSavePayment(ExternalPaymentRequestDTO request) {
        log.info("Ulazak u metodu payin(PaymentRequest payinRequest)");

        //ako hocemo da proverimo da vec nije sniemljna ista transakcija
        PaymentStatus status = PaymentStatus.Created;
        String referenceNumber = request.getReferenceNumber();
        User user = null;

        if (referenceNumber == null || referenceNumber.isEmpty() || Pattern.matches("[0-9]{13}", referenceNumber)) {
            status = PaymentStatus.Cancelled;
        } else {
            user = userMapper.findUserByIdentityNumber(request.getReferenceNumber());
        }
        if (user != null) {
            status = PaymentStatus.Created;
        }

        PaymentTransaction paymentRequest = PaymentTransaction
                .builder()
                .dateCreated(Instant.now())
                .dateModified(Instant.now())
                .paymentType(PaymentType.Payin)
                .provider("rendom banka upisi nesto")
                .paymentStatus(status)
                .user(user)
                .build();

        paymentTransactionMapper.savePaymentTransaction(paymentRequest);
    }


    @Transactional
    public void updateTransactionStatus(PaymentResponseDTO paymentResponse) {

        PaymentTransaction pmt = PaymentTransaction
                .builder()
                .paymentStatus(paymentResponse.getStatus())
                .dateModified(Instant.now())
                .build();

        int numOfUpdated = paymentTransactionMapper.updateTransaction(pmt);

        log.info("Broj update-ovanih transakcija iz statusa AssignedForTomorrow u Assigned: {}", numOfUpdated);
    }


}



