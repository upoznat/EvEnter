package com.eventer.eventticket.amqp.rabbit;

import com.eventer.eventticket.amqp.Queues;
import com.eventer.eventticket.dto.BuyTicketRequest;
import com.eventer.eventticket.dto.WalletRequest;
import com.eventer.eventticket.utils.TicketWalletType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;


@Slf4j
@Component
public class RequestSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Async("payin-request-sending-executor")
    void sendBuyTicketRequest(WalletRequest message) {
        rabbitTemplate.convertAndSend(Queues.WALLET_BUY_TICKET_REQUEST_QUEUE, message);
    }

    @Async("payin-request-sending-executor")
    void sendCancelTicketRequest(WalletRequest message) {
        rabbitTemplate.convertAndSend(Queues.WALLET_CANCEL_TICKET_REQUEST_QUEUE, message);
    }

    public void sendTicketRequestToWallet(WalletRequest message) {
        if(TicketWalletType.BUY.equals(message.getType())) {
            sendBuyTicketRequest(message);
        } else if(TicketWalletType.CANCEL.equals(message.getType())) {
            sendCancelTicketRequest(message);
        }
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
