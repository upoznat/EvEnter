package com.eventer.eventticket.amqp.rabbit;

import com.eventer.eventticket.amqp.configuration.RabbitMQProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.amqp.core.*;

import static com.eventer.eventticket.amqp.Queues.*;
import static com.eventer.eventticket.amqp.rabbit.Exchanges.WALLET_EXCHANGE;

@Configuration
public class MessagingConfiguration {

    @Autowired
    RabbitMQProperties properties;

    @Bean
    Queue buyTicketRequestQueue() {
        return new Queue(WALLET_BUY_TICKET_REQUEST_QUEUE, false);
    }

    @Bean
    Queue buyTicketResponseQueue() {
        return new Queue(WALLET_BUY_TICKET_RESPONSE_QUEUE, false);
    }
    @Bean
     Queue cancelTicketRequestQueue() {

        return new Queue(WALLET_CANCEL_TICKET_REQUEST_QUEUE, false);
    }

    @Bean
    Queue cancelTicketResponseQueue() {
        return new Queue(WALLET_CANCEL_TICKET_RESPONSE_QUEUE, false);
    }


    @Bean
    FanoutExchange exchange() {
        return new FanoutExchange(WALLET_EXCHANGE);
    }

    @Bean
    Binding bindingTicketRequest(FanoutExchange  exchange) {
        return BindingBuilder.bind(buyTicketRequestQueue()).to(exchange);
    }

    @Bean
    Binding bindingTicketResponse(FanoutExchange  exchange) {
        return BindingBuilder.bind(buyTicketResponseQueue()).to(exchange);
    }
    @Bean
    Binding bindingCancelRequest(FanoutExchange  exchange) {
        return BindingBuilder.bind(cancelTicketRequestQueue()).to(exchange);
    }

    @Bean
    Binding bindingCancelResponse(FanoutExchange  exchange) {
        return BindingBuilder.bind(cancelTicketResponseQueue()).to(exchange);
    }



    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory =
                new CachingConnectionFactory(rabbitConnectionFactory().getRabbitConnectionFactory());
        cachingConnectionFactory.setChannelCacheSize(10);
        cachingConnectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);

        return cachingConnectionFactory;
    }

    public RabbitConnectionFactoryBean rabbitConnectionFactory() {
        RabbitConnectionFactoryBean connectionFactory = new RabbitConnectionFactoryBean();
        connectionFactory.setHost(properties.getWalletExchangeUrl());
        connectionFactory.setUsername(properties.getWalletUsername());
        connectionFactory.setPassword(properties.getWalletPassword());

        return connectionFactory;
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory, MessageConverter converter){
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }

}
