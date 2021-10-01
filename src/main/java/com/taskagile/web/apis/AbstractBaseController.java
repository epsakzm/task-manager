package com.taskagile.web.apis;

import com.taskagile.domain.application.commands.AnonymousCommand;
import com.taskagile.domain.application.commands.UserCommand;
import com.taskagile.domain.model.user.SimpleUser;
import com.taskagile.utils.RequestUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

public abstract class AbstractBaseController {

    void addTriggeredBy(UserCommand command, HttpServletRequest request) {
        command.triggeredBy(((SimpleUser) ((UsernamePasswordAuthenticationToken) request.getUserPrincipal()).getPrincipal()).getUserId(),
                RequestUtils.getIpAddress(request));
    }

    void addTriggeredBy(AnonymousCommand command, HttpServletRequest request) {
        command.triggeredBy(RequestUtils.getIpAddress(request));
    }
}
