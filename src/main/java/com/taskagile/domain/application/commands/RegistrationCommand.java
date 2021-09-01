package com.taskagile.domain.application.commands;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class RegistrationCommand {

    private String username;
    private String emailAddress;
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RegistrationCommand that = (RegistrationCommand) o;

        return new EqualsBuilder().append(username, that.username).append(emailAddress, that.emailAddress).append(password, that.password).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(username).append(emailAddress).append(password).toHashCode();
    }

    public RegistrationCommand(String username, String emailAddress, String password) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }
}
