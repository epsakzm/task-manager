package com.taskagile.domain.application.commands;

import com.taskagile.domain.common.event.TriggeredFrom;
import com.taskagile.domain.common.model.IpAddress;
import io.jsonwebtoken.lang.Assert;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class AnonymousCommand implements TriggeredFrom {

    private IpAddress ipAddress;

    public void triggeredBy(IpAddress ipAddress) {
        Assert.notNull(ipAddress);

        this.ipAddress = ipAddress;
    }

    @Override
    public IpAddress getIpAddress() {
        return ipAddress;
    }
}
