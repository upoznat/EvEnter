package com.eventer.eventticket.amqp.rabbit;

import com.eventer.eventticket.amqp.Queues;
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
public class PaymentResponseSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Async("payin-response-sending-executor")
    public void sendBuyTicketRequest(String message) {
        rabbitTemplate.convertAndSend(Queues.WALLET_BUY_TICKET_REQUEST_QUEUE, message);
    }

    @Async("payin-response-sending-executor")
    public void sendCancelTicketRequest(String message) {
        rabbitTemplate.convertAndSend(Queues.WALLET_CANCEL_TICKET_REQUEST_QUEUE, message);
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
