package com.taskagile.domain.model.card.events;

import com.taskagile.domain.common.event.DomainEvent;
import com.taskagile.domain.common.event.TriggeredBy;
import com.taskagile.domain.model.card.Card;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CardTitleChangedEvent extends CardDomainEvent {

    private String beforeTitle;
    private String afterTitle;

    public CardTitleChangedEvent(Card card, String beforeTitle, TriggeredBy triggeredBy) {
        super(card.getId(), card.getTitle(), card.getBoardId(), triggeredBy);
        this.afterTitle = card.getTitle();
        this.beforeTitle = beforeTitle;
    }
}
