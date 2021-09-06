package com.taskagile.domain.common.mail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DefaultMailManager implements MailManager {

    private static final Logger log = LoggerFactory.getLogger(DefaultMailManager.class);

    private String mailFrom;
    private Mailer mailer;
    private Configuration configuration;

    public DefaultMailManager(@Value("${app.mail-from") String mailFrom,
                              Mailer mailer,
                              Configuration configuration) {
        this.mailFrom = mailFrom;
        this.mailer = mailer;
        this.configuration = configuration;
    }

    @Override
    public void send(String emailAddress, String subject, String template, MessageVariable... variables) {
        Assert.hasText(emailAddress, "Parameter `emailAddress` must not be blank");
        Assert.hasText(subject, "Parameter `subject` must not be blank");
        Assert.hasText(template, "Parameter `template` must not be blank");

        mailer.send(new SimpleMessage(emailAddress, subject, createMessageBody(template, variables), mailFrom));
    }

    private String createMessageBody(String templateName, MessageVariable... variables) {
        try {
            Template template = configuration.getTemplate(templateName);
            Map<String, Object> model = new HashMap<>();
            if (variables != null) {
               model = Arrays.stream(variables).collect(Collectors.toMap(MessageVariable::getKey, MessageVariable::getValue));
            }
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            log.error("Failed to create message body from template '" + templateName + "'", e);
            return null;
        }
    }
}
