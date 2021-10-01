package com.taskagile.domain.model.card.events;

import com.taskagile.domain.common.event.DomainEvent;
import com.taskagile.domain.common.event.TriggeredBy;
import com.taskagile.domain.model.card.Card;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CardAddedEvent extends CardDomainEvent {

    public CardAddedEvent(Card card, TriggeredBy triggeredBy) {
        super(card.getId(), card.getTitle(), card.getBoardId(), triggeredBy);
    }
}
