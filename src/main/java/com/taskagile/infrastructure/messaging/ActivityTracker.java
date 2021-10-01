package com.taskagile.infrastructure.messaging;

import com.taskagile.domain.application.ActivityService;
import com.taskagile.domain.common.event.DomainEvent;
import com.taskagile.domain.model.activity.Activity;
import com.taskagile.domain.model.activity.DomainEventToActivityConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ActivityTracker {

    private final ActivityService activityService;
    private final DomainEventToActivityConverter converter;

    @RabbitListener(queues = "#{activityTrackingQueue.name}")
    public void receive(DomainEvent domainEvent) {
        log.debug("Receive Domain Event : {}", domainEvent);

        Activity activity = converter.toActivity(domainEvent);
        if (activity != null) {
            activityService.saveActivity(activity);
        }
    }
}
