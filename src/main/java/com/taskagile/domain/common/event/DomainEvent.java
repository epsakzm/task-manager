package com.taskagile.domain.common.event;

import com.taskagile.domain.common.model.IpAddress;
import com.taskagile.domain.model.user.UserId;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.io.Serializable;
import java.util.Date;

@Getter
public abstract class DomainEvent implements Serializable {

    private UserId userId;
    private IpAddress ipAddress;
    private Date occurredAt;

    public DomainEvent(TriggeredBy triggeredBy) {
        this.userId = triggeredBy.getUserId();
        this.ipAddress = triggeredBy.getIpAddress();
        this.occurredAt = new Date();
    }

    public DomainEvent(UserId userId, TriggeredFrom triggeredFrom) {
        this.userId = userId;
        this.ipAddress = triggeredFrom.getIpAddress();
        this.occurredAt = new Date();
    }
}
