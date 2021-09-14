package com.taskagile.domain.application.commands;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.util.Assert;

public class RegistrationCommand {

    private final String username;
    private final String emailAddress;
    private final String firstName;
    private final String lastName;
    private final String password;

    public RegistrationCommand(String username, String emailAddress, String firstName, String lastName, String password) {
        Assert.hasText(username, "Parameter `username` must not be empty");
        Assert.hasText(emailAddress, "Parameter `emailAddress` must not be empty");
        Assert.hasText(firstName, "Parameter `firstName` must not be empty");
        Assert.hasText(lastName, "Parameter `lastName` must not be empty");
        Assert.hasText(password, "Parameter `password` must not be empty");

        this.username = username;
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RegistrationCommand that = (RegistrationCommand) o;

        return new EqualsBuilder().append(username, that.username).append(emailAddress, that.emailAddress).append(firstName, that.firstName).append(lastName, that.lastName).append(password, that.password).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(username).append(emailAddress).append(firstName).append(lastName).append(password).toHashCode();
    }

    @Override
    public String toString() {
        return "RegistrationCommand{" +
                "username='" + username + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
