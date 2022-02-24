package com.eventer.eventticket.amqp.rabbit;

import com.eventer.eventticket.amqp.Queues;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class PaymentRequestListener {


    @Autowired
    PaymentResponseSender sender;

    @Async("payin-request-executor")
    @RabbitListener(queues = Queues.WALLET_BUY_TICKET_RESPONSE_QUEUE)
    public void receiveWalletPayinResponse() {
        log.info("Recieved Message From RabbitMQ: ");
        try {

            //sender.sendPayinResponse(resp);
        } catch (Exception e) {
            log.info("STAVI RETRY ZA OPTIMISTIK LOK");
        }

    }

    @Async("withdraw-request-executor")
    @RabbitListener(queues = Queues.WALLET_CANCEL_TICKET_RESPONSE_QUEUE)
    public void receiveWalletWithdrawResponse() {
        log.info("Recieved Message From RabbitMQ: " );
        try {

            //sender.sendWithdrawResponse(resp);
        } catch (Exception e) {
            log.info("STAVI RETRY ZA OPTIMISTIK LOK");
        }

    }

}
