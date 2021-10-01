package com.taskagile.domain.model.user.events;

import com.taskagile.domain.common.event.DomainEvent;
import com.taskagile.domain.common.event.TriggeredBy;
import com.taskagile.domain.common.event.TriggeredFrom;
import com.taskagile.domain.model.user.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.util.Assert;

@EqualsAndHashCode
@ToString
@Getter
public class UserRegisteredEvent extends DomainEvent {

    public UserRegisteredEvent(User user, TriggeredFrom triggeredFrom) {
        super(user.getId(), triggeredFrom);
    }
}
