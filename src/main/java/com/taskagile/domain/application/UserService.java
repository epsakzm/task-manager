package com.taskagile.domain.application;

import com.taskagile.domain.application.commands.RegisterCommand;
import com.taskagile.domain.model.user.RegistrationException;
import com.taskagile.domain.model.user.User;
import com.taskagile.domain.model.user.UserId;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {

    User findById(UserId userId);

    void register(RegisterCommand registerCommand) throws RegistrationException;
}
