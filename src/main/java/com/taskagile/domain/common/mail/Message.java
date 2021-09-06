package com.taskagile.domain.common.mail;

public interface Message {

    /**
     * @return recipient's email address
     */
    String getTo();

    String getSubject();

    String getBody();

    String getFrom();
}
