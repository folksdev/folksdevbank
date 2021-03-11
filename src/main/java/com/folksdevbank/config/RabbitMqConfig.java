package com.folksdevbank.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${sample.rabbitmq.exchange}")
    String exchange;

    @Value("${sample.rabbitmq.queue}")
    String queueName;

    @Value("${sample.rabbitmq.routingKey}")
    String routingKey;


    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Queue firstStepQueue(){
        return new Queue(queueName, false);
    }

    @Bean
    Queue secondStepQueue() {
        return new Queue("secondStepQueue", true);
    }

    @Bean
    Queue thirdStepQueue() {
        return new Queue("thirdStepQueue", true);
    }

    @Bean
    Binding binding(Queue firstStepQueue, DirectExchange exchange){
        return BindingBuilder.bind(firstStepQueue).to(exchange).with(routingKey);
    }

    @Bean
    Binding secondBinding(Queue secondStepQueue, DirectExchange exchange){
        return BindingBuilder.bind(secondStepQueue).to(exchange).with("secondRoute");
    }

    @Bean
    Binding thirdBinding(Queue thirdStepQueue, DirectExchange exchange){
        return BindingBuilder.bind(thirdStepQueue).to(exchange).with("thirdRoute");
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }


}
