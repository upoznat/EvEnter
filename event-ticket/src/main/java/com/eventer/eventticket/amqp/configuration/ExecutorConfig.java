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

    @Bean("payin-request-executor")
    public ThreadPoolTaskExecutor processPayinRequestExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(properties.getPaymentInPoolSize());
        executor.setThreadNamePrefix("payment-request-executor");
        executor.setAwaitTerminationSeconds(properties.getPaymentInAwaitTerminationSeconds());
        executor.initialize();

        return executor;
    }

    @Bean("payin-response-sending-executor")
    public ThreadPoolTaskExecutor resultSendExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(properties.getPaymentInPoolSize());
        executor.setThreadNamePrefix("payin-response-sending-executor");
        executor.setAwaitTerminationSeconds(properties.getPaymentInAwaitTerminationSeconds());
        executor.initialize();

        return executor;
    }

    @Bean("withdraw-request-executor")
    public ThreadPoolTaskExecutor processWithdrawRequestExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(properties.getPaymentInPoolSize());
        executor.setThreadNamePrefix("payment-request-executor");
        executor.setAwaitTerminationSeconds(properties.getPaymentInAwaitTerminationSeconds());
        executor.initialize();

        return executor;
    }
}
