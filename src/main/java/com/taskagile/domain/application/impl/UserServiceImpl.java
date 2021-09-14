package com.taskagile.domain.application.impl;

import com.taskagile.domain.application.commands.RegistrationCommand;
import com.taskagile.domain.common.event.DomainEventPublisher;
import com.taskagile.domain.common.mail.MailManager;
import com.taskagile.domain.common.mail.MessageVariable;
import com.taskagile.domain.model.user.*;
import com.taskagile.domain.application.UserService;
import com.taskagile.domain.model.user.events.UserRegisteredEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final RegistrationManagement registrationManagement;
    private final DomainEventPublisher domainEventPublisher;
    private final MailManager mailManager;
    private final UserRepository userRepository;

    public UserServiceImpl(RegistrationManagement registrationManagement, DomainEventPublisher domainEventPublisher, MailManager mailManager, UserRepository userRepository) {
        this.registrationManagement = registrationManagement;
        this.domainEventPublisher = domainEventPublisher;
        this.mailManager = mailManager;
        this.userRepository = userRepository;
    }

    @Override
    public User findById(UserId userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void register(RegistrationCommand command) throws RegistrationException {
        Assert.notNull(command, "Parameter `command must not be null");
        User newUser = registrationManagement.register(command.getUsername(),
                command.getEmailAddress(),
                command.getFirstName(),
                command.getLastName(),
                command.getPassword());
        sendWelcomeMessage(newUser);
        domainEventPublisher.publish(new UserRegisteredEvent(this, newUser));
    }

    private void sendWelcomeMessage(User user) {
        mailManager.send(
            user.getEmailAddress(),
                "Welcome to TaskAgile",
                "welcome.ftl",
                MessageVariable.from("user", user)
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("No user found");
        }
        User user;
        if (username.contains("@")) {
            user = userRepository.findByEmailAddress(username);
        } else {
            user = userRepository.findByUsername(username);
        }
        if (user == null) {
            throw new UsernameNotFoundException("No user found by '" + username + "'");
        }
        return new SimpleUser(user);
    }
}
