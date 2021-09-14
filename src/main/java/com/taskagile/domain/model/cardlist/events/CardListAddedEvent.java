package com.taskagile.domain.model.cardlist.events;

import com.taskagile.domain.common.event.DomainEvent;
import com.taskagile.domain.model.cardlist.CardList;

public class CardListAddedEvent extends DomainEvent {

    private CardList cardList;

    public CardListAddedEvent(Object source, CardList cardList) {
        super(source);
        this.cardList = cardList;
    }

    public CardList getCardList() {
        return cardList;
    }
}
