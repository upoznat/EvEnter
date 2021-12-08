package com.eventer.wallet.amqp.rabbit;

import com.eventer.wallet.amqp.Queues;
import com.eventer.wallet.dto.PaymentResponseDTO;
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
    public void send(PaymentResponseDTO message) {
        rabbitTemplate.convertAndSend(Queues.WALLET_PAYIN_RESPONSE_QUEUE, message);
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
