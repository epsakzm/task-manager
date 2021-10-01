package com.taskagile.domain.application.commands;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.util.Assert;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Getter
@AllArgsConstructor
public class RegisterCommand extends AnonymousCommand {

    private final String username;
    private final String emailAddress;
    private final String firstName;
    private final String lastName;
    private final String password;
}
