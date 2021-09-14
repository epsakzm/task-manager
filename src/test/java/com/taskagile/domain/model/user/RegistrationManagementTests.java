package com.taskagile.domain.model.user;

import com.taskagile.domain.common.security.PasswordEncryptor;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrationManagementTests {

    private UserRepository repositoryMock;
    private PasswordEncryptor passwordEncryptorMock;
    private RegistrationManagement instance;

    public RegistrationManagementTests() {
        repositoryMock = mock(UserRepository.class);
        passwordEncryptorMock = mock(PasswordEncryptor.class);
        instance = new RegistrationManagement(repositoryMock, passwordEncryptorMock);
    }

    @Test
    void register_existedUsername_shouldFail() throws RegistrationException {
        String username = "existUsername";
        String emailAddress = "email@email.com";
        String password = "myPassword1";

        when(repositoryMock.findByUsername(username)).thenReturn(new User());
        assertThrows(UsernameExistsException.class, () -> instance.register(username, emailAddress, "Test", "User", password));
    }

    @Test
    void register_existedEmailAddress_shouldFail() throws RegistrationException {
        String username = "username";
        String emailAddress = "existed@email.com";
        String password = "password1";

        when(repositoryMock.findByEmailAddress(emailAddress)).thenReturn(new User());
        assertThrows(EmailAddressExistsException.class, () -> instance.register(username, emailAddress, "Test", "User", password));
    }

    @Test
    void register_uppercaseEmailAddress_shouldSucceedAndBecomeLowercase() throws RegistrationException {
        String username = "username";
        String emailAddress = "Uppercase@email.com";
        String password = "password1";

        instance.register(username, emailAddress, "Test", "User", password);
        User userToSave = User.create(username, emailAddress.toLowerCase(), "Test", "User", password);
        verify(repositoryMock).save(userToSave);
    }

    @Test
    void register_newUser_shouldSucceed() throws RegistrationException {
        String username = "username";
        String emailAddress = "username@email.com";
        String password = "password";
        String encryptedPassword = "EncryptedPassword";
        User newUser = User.create(username, emailAddress, "Test", "User", encryptedPassword);

        when(repositoryMock.findByUsername(username)).thenReturn(null);
        when(repositoryMock.findByEmailAddress(emailAddress)).thenReturn(null);
        doNothing().when(repositoryMock).save(newUser);
        when(passwordEncryptorMock.encrypt(password)).thenReturn("EncryptedPassword");

        User savedUser = instance.register(username, emailAddress, "Test", "User", password);
        InOrder inOrder = inOrder(repositoryMock);
        inOrder.verify(repositoryMock).findByUsername(username);
        inOrder.verify(repositoryMock).findByEmailAddress(emailAddress);
        inOrder.verify(repositoryMock).save(newUser);
        verify(passwordEncryptorMock).encrypt(password);
        assertEquals(encryptedPassword, savedUser.getPassword(), "Saved user's password should be encrypted");
    }
}
