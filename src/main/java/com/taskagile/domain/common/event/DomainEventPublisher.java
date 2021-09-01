package com.taskagile.domain.common.event;

import org.springframework.stereotype.Component;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
