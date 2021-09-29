package com.taskagile.domain.model.card.events;

import com.taskagile.domain.common.event.DomainEvent;
import com.taskagile.domain.model.card.Card;
import lombok.Getter;

@Getter
public class CardTitleChangedEvent extends DomainEvent {

    private Card card;

    public CardTitleChangedEvent(Object source, Card card) {
        super(source);
        this.card = card;
    }
}
