package com.taskagile.domain.application.commands;

import com.taskagile.domain.common.event.TriggeredBy;
import com.taskagile.domain.common.model.IpAddress;
import com.taskagile.domain.model.user.UserId;
import io.jsonwebtoken.lang.Assert;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class UserCommand implements TriggeredBy {

    private UserId userId;
    private IpAddress ipAddress;

    public void triggeredBy(UserId userId, IpAddress ipAddress) {
        Assert.notNull(userId);
        Assert.notNull(ipAddress);

        this.userId = userId;
        this.ipAddress = ipAddress;
    }

    @Override
    public UserId getUserId() {
        return userId;
    }

    @Override
    public IpAddress getIpAddress() {
        return ipAddress;
    }
}
