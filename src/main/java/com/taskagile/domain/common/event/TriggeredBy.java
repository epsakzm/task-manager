package com.taskagile.domain.common.event;

import com.taskagile.domain.common.model.IpAddress;
import com.taskagile.domain.model.user.UserId;

public interface TriggeredBy {

    UserId getUserId();

    IpAddress getIpAddress();

}
