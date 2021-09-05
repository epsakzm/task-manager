package com.taskagile.web.apis.authenticate;

import com.taskagile.utils.JsonUtils;
import com.taskagile.web.results.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SimpleAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        ApiResult failure;
        if (e instanceof BadCredentialsException) {
            failure = ApiResult.message("Invalid credentials");
        } else if (e instanceof InsufficientAuthenticationException) {
            failure = ApiResult.message("Invalid authentication request");
        } else {
            failure = ApiResult.message("Authentication Failure");
        }
        JsonUtils.write(httpServletResponse.getWriter(), failure);
    }
}
