package com.eventer.eventticket.amqp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableConfigurationProperties(RabbitMQProperties.class)
public class ExecutorConfig {

    @Autowired
    private RabbitMQProperties properties;

    @Bean("buy-ticket-request-executor")
    public ThreadPoolTaskExecutor buyTicketRequestExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(properties.getPaymentInPoolSize());
        executor.setThreadNamePrefix("ticket-executor");
        executor.setAwaitTerminationSeconds(properties.getPaymentInAwaitTerminationSeconds());
        executor.initialize();

        return executor;
    }

    @Bean("buy-ticket-response-executor")
    public ThreadPoolTaskExecutor buyTicketResponseExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(properties.getPaymentInPoolSize());
        executor.setThreadNamePrefix("ticket-executor");
        executor.setAwaitTerminationSeconds(properties.getPaymentInAwaitTerminationSeconds());
        executor.initialize();

        return executor;
    }

    @Bean("cancel-ticket-request-executor")
    public ThreadPoolTaskExecutor cancelTicketRequestExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(properties.getPaymentInPoolSize());
        executor.setThreadNamePrefix("ticket-executor");
        executor.setAwaitTerminationSeconds(properties.getPaymentInAwaitTerminationSeconds());
        executor.initialize();

        return executor;
    }

    @Bean("cancel-ticket-response-executor")
    public ThreadPoolTaskExecutor cancelTicketResponseExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(properties.getPaymentInPoolSize());
        executor.setThreadNamePrefix("ticket-executor");
        executor.setAwaitTerminationSeconds(properties.getPaymentInAwaitTerminationSeconds());
        executor.initialize();

        return executor;
    }

}
