package com.github.amitsureshchandra.codejudge.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("mq_test")
@Configuration
public class RMQConfig {
    @Bean
    Queue codeQueue(@Value("${mq-code-event-queue}") String queueName) {
        System.out.println("1122345");
        return new Queue(queueName);
    }

    @Bean
    TopicExchange exchange( @Value("${mq-code-event-exchange}") String exchangeName) {
        return new TopicExchange(exchangeName);
    }

    @Bean
    Binding codeBinding(Queue codeQueue, TopicExchange exchange) {
        return BindingBuilder.bind(codeQueue).to(exchange).with("code");
    }
}
