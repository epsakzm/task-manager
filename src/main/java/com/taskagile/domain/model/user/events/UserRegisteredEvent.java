package com.taskagile.domain.model.user.events;

import com.taskagile.domain.common.event.DomainEvent;
import com.taskagile.domain.model.user.User;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.util.Assert;

public class UserRegisteredEvent extends DomainEvent {

    private User user;

    public UserRegisteredEvent(Object source, User user) {
        super(source);
        Assert.notNull(user, "Parameter `user` must not be null");
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserRegisteredEvent that = (UserRegisteredEvent) o;

        return new EqualsBuilder().append(user, that.user).isEquals();
    }

    @Override
    public int hashCode() {
        return this.user.hashCode();
    }

    @Override
    public String toString() {
        return "UserRegisteredEvent{" +
            "user=" + user +
            "timestamp=" + getTimestamp() +
            '}';
    }
}
