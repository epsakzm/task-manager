package com.taskagile.infrastructure.messaging;

import com.taskagile.domain.common.event.DomainEvent;
import com.taskagile.domain.common.event.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AmqpDomainEventPublisher implements DomainEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final FanoutExchange exchange;

    public AmqpDomainEventPublisher(RabbitTemplate rabbitTemplate,
                                    @Qualifier("domainEventsExchange") FanoutExchange exchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    @Override
    public void publish(DomainEvent event) {
        log.debug("Publish event : {}", event);
        try {
            rabbitTemplate.convertAndSend(exchange.getName(), "", event);
        } catch (AmqpException e) {
            log.error("Failed to send event : " + event, e);
        }
    }
}
