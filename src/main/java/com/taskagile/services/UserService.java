package com.taskagile.services;

import com.taskagile.web.payload.RegistrationPayload;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  public String register(RegistrationPayload payload) {
    return payload.getUsername();
  }
}
