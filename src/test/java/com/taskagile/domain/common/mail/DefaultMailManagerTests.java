package com.taskagile.domain.common.mail;

import freemarker.template.Configuration;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@SpringBootTest
class DefaultMailManagerTests {

    @Autowired
    private Configuration configuration;
    private Mailer mailerMock;
    private DefaultMailManager instance;

    @BeforeEach
    void setUp() {
        mailerMock = mock(Mailer.class);
        instance = new DefaultMailManager("noreply@taskagile.com", mailerMock, configuration);
    }

    @Test
    void send_nullEmailAddress_shouldFail() {
        assertThrows(IllegalArgumentException.class, () -> instance.send(null, "Test subject", "test.ftl"));
    }

    @Test
    void send_emptyEmailAddress_shouldFail() {
        assertThrows(IllegalArgumentException.class, () -> instance.send("", "Test subject", "test.ftl"));
    }

    @Test
    void send_nullSubject_shouldFail() {
        assertThrows(IllegalArgumentException.class, () -> instance.send("test@taskagile.com", null, "test.ftl"));
    }

    @Test
    void send_emptySubject_shouldFail() {
        assertThrows(IllegalArgumentException.class, () -> instance.send("test@taskagile.com", "", "test.ftl"));
    }

    @Test
    void send_nullTemplateName_shouldFail() {
        assertThrows(IllegalArgumentException.class, () -> instance.send("test@taskagile.com", "Test subject", null));
    }

    @Test
    void send_emptyTemplateName_shouldFail() {
        assertThrows(IllegalArgumentException.class, () -> instance.send("test@taskagile.com", "Test subject", ""));
    }

    @Test
    void send_validParameters_shouldSucceed() {
        String to = "user@example.com";
        String subject = "Test subject";
        String templateName = "test.ftl";

        instance.send(to, subject, templateName, MessageVariable.from("name", "test"));
        ArgumentCaptor<Message> argumentCaptor = ArgumentCaptor.forClass(Message.class);
        verify(mailerMock).send(argumentCaptor.capture());

        Message messageSent = argumentCaptor.getValue();
        assertEquals(to, messageSent.getTo());
        assertEquals(subject, messageSent.getSubject());
        assertEquals("noreply@taskagile.com", messageSent.getFrom());
        assertEquals("Hello, test\n", messageSent.getBody());
    }
}