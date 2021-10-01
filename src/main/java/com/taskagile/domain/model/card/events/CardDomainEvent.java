package com.taskagile.domain.model.card.events;

import com.taskagile.domain.common.event.DomainEvent;
import com.taskagile.domain.common.event.TriggeredBy;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.card.CardId;
import lombok.Getter;

@Getter
public abstract class CardDomainEvent extends DomainEvent {

    private CardId cardId;
    private String cardTitle;
    private BoardId boardId;

    public CardDomainEvent(CardId cardId, String cardTitle, BoardId boardId, TriggeredBy triggeredBy) {
        super(triggeredBy);
        this.cardId = cardId;
        this.cardTitle = cardTitle;
        this.boardId = boardId;
    }
}
