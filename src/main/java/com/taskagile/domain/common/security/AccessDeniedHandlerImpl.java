package com.taskagile.domain.common.security;

import com.taskagile.domain.model.user.SimpleUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    private static final Logger log = LoggerFactory.getLogger(AccessDeniedHandlerImpl.class);

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {
        if (log.isDebugEnabled()) {
            log.debug(httpServletRequest.getRequestURI() + " 요청 경로 차단됨.");
        }

        if (httpServletRequest.getRequestURI().startsWith("/api/")) {
            if (httpServletRequest.getUserPrincipal() instanceof SimpleUser) {
                httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            } else {
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            httpServletResponse.sendRedirect("/login");
        }
    }
}
