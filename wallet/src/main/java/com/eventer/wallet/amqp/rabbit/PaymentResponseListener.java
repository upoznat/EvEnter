package com.eventer.wallet.amqp.rabbit;

import com.eventer.wallet.amqp.Queues;
import com.eventer.wallet.domain.UserTransaction;
import com.eventer.wallet.dto.PaymentResponseDTO;
import com.eventer.wallet.service.UserTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class PaymentResponseListener {

    @Autowired
    UserTransactionService customerService;

    @Autowired
    PaymentResponseSender sender;

    @Async("payment-request-executor")
    @RabbitListener(queues = Queues.WALLET_PAYIN_REQUEST_QUEUE)
    public void receiveWalletPayinResponse(UserTransaction pmt) {
        log.info("Recieved Message From RabbitMQ: " + pmt);
                PaymentResponseDTO resp = customerService.processPaymentRequest(pmt);
                try{
                    sender.send(resp);
                } catch(Exception e){
                    log.info("STAVI RETRY ZA OPTIMISTIK LOK");
                }

    }

}
