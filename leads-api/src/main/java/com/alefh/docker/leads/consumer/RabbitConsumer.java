package com.alefh.docker.leads.consumer;

import com.alefh.docker.leads.domain.Lead;
import com.alefh.docker.leads.repository.LeadRepository;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RabbitConsumer {

    private LeadRepository leadRepository;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void onMessage(Lead lead) {
        System.out.print("lendo msg: " + lead.toString());
        leadRepository.save(lead);
    }
}
