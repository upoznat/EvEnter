package com.eventer.paymentservice.amqp.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@ConfigurationProperties(prefix = "eventer.payment-service.rabbit")
public class RabbitMQProperties {

    private Integer paymentInPoolSize = 5;
    private int paymentInAwaitTerminationSeconds = 5;
    private long paymentInTTL = 24 * 60 * 60 * 1000L;

    private String walletExchangeUrl = "localhost";
    private String walletUsername = "guest";
    private String walletPassword = "guest";
}
