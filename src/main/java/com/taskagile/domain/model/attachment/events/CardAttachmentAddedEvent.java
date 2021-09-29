package com.taskagile.domain.model.attachment.events;

import com.taskagile.domain.common.event.DomainEvent;
import com.taskagile.domain.model.attachment.Attachment;
import lombok.Getter;

@Getter
public class CardAttachmentAddedEvent extends DomainEvent {

    private Attachment attachment;

    public CardAttachmentAddedEvent(Object source, Attachment attachment) {
        super(source);
        this.attachment = attachment;
    }
}
