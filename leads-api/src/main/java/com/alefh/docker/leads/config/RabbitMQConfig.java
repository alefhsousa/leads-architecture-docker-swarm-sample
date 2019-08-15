package com.alefh.docker.leads.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.name}")
    private String leadsQueue;
    @Value("${rabbitmq.topic.name}")
    private String leadsInput;


    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Binding bindingTopiocEventosComFilaAlunos(Queue queueAlunos, FanoutExchange leadTopic) {
        return BindingBuilder.bind(queueAlunos).to(leadTopic);
    }

    @Bean
    public Queue queueLead() {
        return new Queue(leadsQueue);
    }

    @Bean
    public FanoutExchange leadTopic() {
        return new FanoutExchange(leadsInput);
    }
}