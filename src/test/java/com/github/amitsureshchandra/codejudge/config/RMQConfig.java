package com.github.amitsureshchandra.codejudge.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("mq_test")
@Configuration
public class RMQConfig {
    @Bean
    Queue codeQueue() {
        return new Queue(MQConfig.queueName);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(MQConfig.exchangeName);
    }

    @Bean
    Binding codeBinding(Queue codeQueue, TopicExchange exchange) {
        return BindingBuilder.bind(codeQueue).to(exchange).with("code");
    }
}
