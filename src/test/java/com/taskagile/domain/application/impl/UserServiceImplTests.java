package com.taskagile.domain.application.impl;

import com.taskagile.domain.application.commands.RegistrationCommand;
import com.taskagile.domain.common.event.DomainEventPublisher;
import com.taskagile.domain.common.mail.MailManager;
import com.taskagile.domain.model.user.EmailAddressExistsException;
import com.taskagile.domain.model.user.RegistrationException;
import com.taskagile.domain.model.user.RegistrationManagement;
import com.taskagile.domain.model.user.UsernameExistsException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTests {

    private RegistrationManagement registrationManagementMock;
    private DomainEventPublisher eventPublisherMock;
    private MailManager mailManagerMock;
    private UserServiceImpl instance;

    public UserServiceImplTests() {
        setUp();
    }

    public void setUp() {
        registrationManagementMock = mock (RegistrationManagement.class);
        eventPublisherMock = mock(DomainEventPublisher.class);
        mailManagerMock = mock(MailManager.class);
        instance = new UserServiceImpl(registrationManagementMock, eventPublisherMock, mailManagerMock);
    }

    @Test
    void register_nullCommand_shouldFail() throws RegistrationException {
        assertThrows(IllegalArgumentException.class, () -> {
           instance.register(null);
        });
    }

    @Test
    void register_existingUsername_shouldFail() throws RegistrationException {
        String username = "username1";
        String emailAddress = "username1@email.com";
        String password = "password";
        doThrow(EmailAddressExistsException.class).when(registrationManagementMock)
            .register(username, emailAddress, password);

        assertThrows(EmailAddressExistsException.class,
            () -> instance.register(new RegistrationCommand(username, emailAddress, password)));
    }
}
