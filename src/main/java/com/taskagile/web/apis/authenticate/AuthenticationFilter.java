package com.taskagile.web.apis.authenticate;

import com.taskagile.utils.JsonUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

    public AuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/authentications", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.debug("로그인 시도 요청");

        String requestBody = IOUtils.toString(request.getReader());
        LoginRequest loginRequest = JsonUtils.toObject(requestBody, LoginRequest.class);

        if (loginRequest == null || loginRequest.isInvalid()) {
            throw new InsufficientAuthenticationException("인증 요청이 유효하지 않습니다 !");
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        return this.getAuthenticationManager().authenticate(token);
    }

    private static class LoginRequest {
        private String username;
        private String password;

        public boolean isInvalid() {
            return StringUtils.isBlank(username) || StringUtils.isBlank(password);
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "LoginRequest{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
}
