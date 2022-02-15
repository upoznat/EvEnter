package com.eventer.paymentservice.amqp.rabbit;

import com.eventer.paymentservice.amqp.Queues;
import com.eventer.paymentservice.dto.PaymentResponseDTO;
import com.eventer.paymentservice.service.PaymentTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class PaymentResponseListener {

    @Autowired
    PaymentTransactionService paymentService;

    @RabbitListener(queues = Queues.WALLET_PAYIN_REQUEST_QUEUE)
    public void receiveWalletPayinResponse(PaymentResponseDTO pmt) {
        log.info("Recieved Message From RabbitMQ: " + pmt);
                paymentService.updateTransactionStatus(pmt);
    }

}
