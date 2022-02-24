package com.eventer.eventticket.amqp.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "eventer.eventticket.rabbit")
public class RabbitMQProperties {

    private Integer paymentInPoolSize = 5;
    private int paymentInAwaitTerminationSeconds = 5;
    private long paymentInTTL = 24 * 60 * 60 * 1000L;

    private String walletExchangeUrl = "localhost";
    private String walletUsername = "guest";
    private String walletPassword = "guest";
}
