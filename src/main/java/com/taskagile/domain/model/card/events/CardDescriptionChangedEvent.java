package com.taskagile.domain.model.card.events;

import com.taskagile.domain.common.event.DomainEvent;
import com.taskagile.domain.common.event.TriggeredBy;
import com.taskagile.domain.model.board.Board;
import com.taskagile.domain.model.board.events.BoardDomainEvent;
import com.taskagile.domain.model.card.Card;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CardDescriptionChangedEvent extends CardDomainEvent {

    private String beforeDescription;
    private String afterDescription;

    public CardDescriptionChangedEvent(Card card, String beforeDescription, TriggeredBy triggeredBy) {
        super(card.getId(), card.getTitle(), card.getBoardId(), triggeredBy);
        this.beforeDescription = beforeDescription;
        this.afterDescription = card.getDescription();
    }
}
