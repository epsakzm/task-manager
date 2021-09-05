package com.taskagile.web.apis.authenticate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class AuthenticationFilterTests {

    @MockBean
    private AuthenticationManager authenticationManagerMock;

    @Test
    void attemptAuthentication_emptyRequestBody_shouldFail() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/authentications");
        AuthenticationFilter filter = new AuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerMock);
        assertThrows(InsufficientAuthenticationException.class, () -> filter.attemptAuthentication(request, new MockHttpServletResponse()));
    }

    @Test
    void attemptAuthentication_invalidJsonStringRequestBody_shouldFail() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/authentications");
        request.setContent("username=testusername&password=TestPassword!".getBytes());
        AuthenticationFilter filter = new AuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerMock);
        assertThrows(InsufficientAuthenticationException.class, () -> filter.attemptAuthentication(request, new MockHttpServletResponse()));
    }

    @Test
    void attemptAuthentication_validJsonStringRequestBody_shouldSucceed() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/authentications");
        request.setContent("{\"username\": \"testusername\", \"password\": \"TestPassword!\"}".getBytes());
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManagerMock);
        authenticationFilter.attemptAuthentication(request, new MockHttpServletResponse());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("testusername", "TestPassword!");
        verify(authenticationManagerMock).authenticate(token);
    }
}