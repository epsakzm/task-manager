package com.taskagile.domain.model.attachment.events;

import com.taskagile.domain.common.event.DomainEvent;
import com.taskagile.domain.common.event.TriggeredBy;
import com.taskagile.domain.model.attachment.Attachment;
import com.taskagile.domain.model.attachment.AttachmentId;
import com.taskagile.domain.model.card.Card;
import com.taskagile.domain.model.card.events.CardDomainEvent;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CardAttachmentAddedEvent extends CardDomainEvent {

    private Attachment attachment;
    private String cardTitle;
    private AttachmentId attachmentId;
    private String fileName;

    public CardAttachmentAddedEvent(Card card, Attachment attachment, TriggeredBy triggeredBy) {
        super(card.getId(), card.getTitle(), card.getBoardId(), triggeredBy);
        this.cardTitle = card.getTitle();
        this.attachmentId = attachment.getId();
        this.fileName = attachment.getFileName();
    }
}
