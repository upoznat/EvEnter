package com.eventer.eventticket.amqp.rabbit;

import com.eventer.eventticket.amqp.Queues;
import com.eventer.eventticket.dto.TicketWalletResponse;
import com.eventer.eventticket.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class PaymentRequestListener {

    @Autowired
    private TicketService ticketService;

    @Async("buy-ticket-response-executor")
    @RabbitListener(queues = Queues.WALLET_BUY_TICKET_RESPONSE_QUEUE)
    public void receiveWalletPayinResponse(TicketWalletResponse response) {
        log.info("Recieved Message From RabbitMQ: ");
        try {
            ticketService.resolveReservation(response);
        } catch (Exception e) {
            log.info("STAVI RETRY ZA OPTIMISTIK LOK");
        }

    }

    @Async("cancel-ticket-response-executor")
    @RabbitListener(queues = Queues.WALLET_CANCEL_TICKET_RESPONSE_QUEUE)
    public void receiveWalletWithdrawResponse(TicketWalletResponse response) {
        log.info("Recieved Message From RabbitMQ: " );
        try {
            ticketService.resolveReservation(response);
        } catch (Exception e) {
            log.info("STAVI RETRY ZA OPTIMISTIK LOK");
        }
    }

}
