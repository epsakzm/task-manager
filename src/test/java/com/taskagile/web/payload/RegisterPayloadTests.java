package com.taskagile.web.payload;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RegisterPayloadTests {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void validate_blankPayload_shouldFail() {
        RegisterPayload payload = new RegisterPayload();
        Set<ConstraintViolation<RegisterPayload>> violations = validator.validate(payload);
        assertEquals(5, violations.size());
    }

    @Test
    public void validate_payloadWithInvalidEmail_shouldFail() {
        RegisterPayload payload = new RegisterPayload();
        payload.setEmailAddress("BadEmailAddress");
        payload.setUsername("username");
        payload.setPassword("password");
        Set<ConstraintViolation<RegisterPayload>> violations = validator.validate(payload);
        assertThat(violations.size()).isEqualTo(3);
    }

    @Test
    public void validate_payloadWithEmailAddressLongerThan100_shouldFail() {
        int maxLocalPartLength = 64;
        String localPart = RandomStringUtils.random(maxLocalPartLength, true, true);
        int usedLength = maxLocalPartLength + "@".length() + ".com".length();
        String domain = RandomStringUtils.random(101 - usedLength, true, true);

        RegisterPayload payload = new RegisterPayload();
        payload.setEmailAddress(localPart + "@" + domain + ".com");
        payload.setUsername("MyUsername");
        payload.setPassword("MyPassword");

        Set<ConstraintViolation<RegisterPayload>> violations = validator.validate(payload);
        assertEquals(3, violations.size());
    }

    @Test
    public void validate_payloadWithUsernameShorterThan2_shouldFail() {
        RegisterPayload payload = new RegisterPayload();
        String usernameTooShort = RandomStringUtils.random(1);
        payload.setUsername(usernameTooShort);
        payload.setPassword("MyPassword");
        payload.setEmailAddress("sunny@taskagile.com");

        Set<ConstraintViolation<RegisterPayload>> violations = validator.validate(payload);
        assertEquals(3, violations.size());
    }

    @Test
    public void validate_payloadWithUsernameLongerThan50_shouldFail() {
        RegisterPayload payload = new RegisterPayload();
        String usernameTooLong = RandomStringUtils.random(51);
        payload.setUsername(usernameTooLong);
        payload.setPassword("MyPassword");
        payload.setEmailAddress("sunny@taskagile.com");

        Set<ConstraintViolation<RegisterPayload>> violations = validator.validate(payload);
        assertEquals(3, violations.size());
    }

    @Test
    public void validate_payloadWithPasswordShorterThan6_shouldFail() {
        RegisterPayload payload = new RegisterPayload();
        String passwordTooShort = RandomStringUtils.random(5);
        payload.setPassword(passwordTooShort);
        payload.setUsername("MyUsername");
        payload.setEmailAddress("sunny@taskagile.com");

        Set<ConstraintViolation<RegisterPayload>> violations = validator.validate(payload);
        assertEquals(3, violations.size());
    }

    @Test
    public void validate_payloadWithPasswordLongerThan30_shouldFail() {
        RegisterPayload payload = new RegisterPayload();
        String passwordTooLong = RandomStringUtils.random(31);
        payload.setPassword(passwordTooLong);
        payload.setUsername("MyUsername");
        payload.setEmailAddress("sunny@taskagile.com");

        Set<ConstraintViolation<RegisterPayload>> violations = validator.validate(payload);
        assertEquals(3, violations.size());
    }

}
