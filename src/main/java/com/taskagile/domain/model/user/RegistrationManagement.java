package com.taskagile.domain.model.user;

import com.taskagile.domain.common.security.PasswordEncryptor;
import org.springframework.stereotype.Component;

@Component
public class RegistrationManagement {

    private final UserRepository userRepository;
    private final PasswordEncryptor passwordEncryptor;

    public RegistrationManagement(UserRepository userRepository, PasswordEncryptor passwordEncryptor) {
        this.userRepository = userRepository;
        this.passwordEncryptor = passwordEncryptor;
    }

    public User register(String username, String emailAddress, String firstName, String lastName, String password) throws RegistrationException {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) throw new UsernameExistsException();
        existingUser = userRepository.findByEmailAddress(emailAddress.toLowerCase());
        if (existingUser != null) throw new EmailAddressExistsException();

        String encryptedPassword = passwordEncryptor.encrypt(password);
        User newUser = User.create(username, emailAddress.toLowerCase(), firstName, lastName, encryptedPassword);
        userRepository.save(newUser);
        return newUser;
    }
}
