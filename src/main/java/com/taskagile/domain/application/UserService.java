package com.taskagile.domain.application;

import com.taskagile.domain.application.commands.RegistrationCommand;
import com.taskagile.domain.model.user.RegistrationException;
import com.taskagile.web.payload.RegistrationPayload;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void register(RegistrationCommand registrationCommand) throws RegistrationException;
}
