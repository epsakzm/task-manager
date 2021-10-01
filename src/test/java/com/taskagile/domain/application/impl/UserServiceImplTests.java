package com.taskagile.domain.application.impl;

import com.taskagile.domain.application.commands.RegisterCommand;
import com.taskagile.domain.common.event.DomainEventPublisher;
import com.taskagile.domain.common.mail.MailManager;
import com.taskagile.domain.model.user.*;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTests {

    private RegistrationManagement registrationManagementMock;
    private DomainEventPublisher eventPublisherMock;
    private MailManager mailManagerMock;
    private UserRepository userRepositoryMock;
    private UserServiceImpl instance;

    public UserServiceImplTests() {
        setUp();
    }

    public void setUp() {
        registrationManagementMock = mock (RegistrationManagement.class);
        eventPublisherMock = mock(DomainEventPublisher.class);
        mailManagerMock = mock(MailManager.class);
        userRepositoryMock = mock(UserRepository.class);

        instance = new UserServiceImpl(registrationManagementMock, eventPublisherMock, mailManagerMock, userRepositoryMock);
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
        String firstName = "Test";
        String lastName = "User";
        doThrow(EmailAddressExistsException.class).when(registrationManagementMock)
            .register(username, emailAddress, firstName, lastName, password);

        assertThrows(EmailAddressExistsException.class,
            () -> instance.register(new RegisterCommand(username, emailAddress, firstName, lastName, password)));
    }

    @Test
    void loadUserByUsername_emptyUsername_shouldFail() {
        Exception exception = null;

        try {
            instance.loadUserByUsername("");
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
        assertTrue(exception instanceof UsernameNotFoundException);
        verify(userRepositoryMock, never()).findByUsername("");
        verify(userRepositoryMock, never()).findByEmailAddress("");
    }

    @Test
    void loadUserByUsername_notExistUsername_shouldFail() {
        String notExistUsername = "NotExistUsername";
        when(userRepositoryMock.findByUsername(notExistUsername)).thenReturn(null);
        Exception exception = null;

        try {
            instance.loadUserByUsername(notExistUsername);
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
        assertTrue(exception instanceof UsernameNotFoundException);
        verify(userRepositoryMock).findByUsername(notExistUsername);
        verify(userRepositoryMock, never()).findByEmailAddress(notExistUsername);
    }

    @Test
    void loadUserByUsername_existUsername_shouldSucceed() throws IllegalAccessException {
        String existUsername = "ExistUsername";
        User foundUser = User.create(existUsername, "user@email.com", "Test", "User", "EncryptedPassword");
        foundUser.updateName("Test", "User");
        FieldUtils.writeField(foundUser, "id", 1L, true);
        when(userRepositoryMock.findByUsername(existUsername)).thenReturn(foundUser);
        Exception exception = null;
        UserDetails userDetails = null;

        try {
            userDetails = instance.loadUserByUsername(existUsername);
        } catch (Exception e) {
            exception = e;
        }

        assertNull(exception);
        verify(userRepositoryMock).findByUsername(existUsername);
        verify(userRepositoryMock, never()).findByEmailAddress(existUsername);
        assertNotNull(userDetails);
        assertEquals(existUsername, userDetails.getUsername());
        assertTrue(userDetails instanceof SimpleUser);
    }

}
