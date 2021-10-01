package com.taskagile.web.apis;

import com.taskagile.domain.application.commands.RegisterCommand;
import com.taskagile.domain.model.user.EmailAddressExistsException;
import com.taskagile.domain.model.user.RegistrationException;
import com.taskagile.domain.model.user.UsernameExistsException;
import com.taskagile.domain.application.UserService;
import com.taskagile.web.payload.RegisterPayload;
import com.taskagile.web.results.ApiResult;
import com.taskagile.web.results.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class RegistrationApiController extends AbstractBaseController {

    private final UserService userService;

    @PostMapping("/api/registrations")
    public ResponseEntity<ApiResult> register(@Valid @RequestBody RegisterPayload payload,
                                              HttpServletRequest request) {
        try {
            RegisterCommand command = payload.toCommand();
            addTriggeredBy(command, request);
            userService.register(command);
            return Result.created();
        } catch (RegistrationException e) {
            String errorMessage = "Registration Failed";
            if (e instanceof UsernameExistsException) {
                errorMessage = "Username already exists";
            } else if (e instanceof EmailAddressExistsException) {
                errorMessage = "Email address already exists";
            }
            return Result.failure(errorMessage);
        }
    }
}
