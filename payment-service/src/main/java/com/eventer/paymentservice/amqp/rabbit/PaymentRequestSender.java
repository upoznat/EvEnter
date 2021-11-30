package com.eventer.paymentservice.amqp.rabbit;

import com.eventer.paymentservice.amqp.Queues;
import com.eventer.paymentservice.domain.PaymentTransaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

@Component
@Slf4j
public class PaymentRequestSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Async("payment-in-sending-executor")
    public void send(PaymentTransaction message) {
        rabbitTemplate.convertAndSend(Queues.WALLET_PAYIN_REQUEST_QUEUE, message);
    }


    private String convertToJSON(Object o) throws JMSException {
        try {
            return new ObjectMapper().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error("Couldn't serialize object to json in order to send it to ActiveMQ!");
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }


}
