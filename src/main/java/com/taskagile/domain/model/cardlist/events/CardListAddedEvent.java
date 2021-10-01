package com.taskagile.domain.model.cardlist.events;

import com.taskagile.domain.common.event.DomainEvent;
import com.taskagile.domain.common.event.TriggeredBy;
import com.taskagile.domain.model.board.events.BoardDomainEvent;
import com.taskagile.domain.model.cardlist.CardList;
import com.taskagile.domain.model.cardlist.CardListId;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CardListAddedEvent extends BoardDomainEvent {

    private CardListId cardListId;
    private String cardListName;

    public CardListAddedEvent(CardList cardList, TriggeredBy triggeredBy) {
        super(cardList.getBoardId(), triggeredBy);
        this.cardListId = cardList.getId();
        this.cardListName = cardList.getName();
    }
}
