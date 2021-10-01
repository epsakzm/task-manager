package com.taskagile.domain.model.activity;

import com.taskagile.domain.common.event.DomainEvent;
import com.taskagile.domain.model.attachment.events.CardAttachmentAddedEvent;
import com.taskagile.domain.model.board.events.BoardCreatedEvent;
import com.taskagile.domain.model.board.events.BoardMemberAddedEvent;
import com.taskagile.domain.model.card.Card;
import com.taskagile.domain.model.card.events.CardAddedEvent;
import com.taskagile.domain.model.card.events.CardDescriptionChangedEvent;
import com.taskagile.domain.model.card.events.CardTitleChangedEvent;
import com.taskagile.domain.model.cardlist.events.CardListAddedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DomainEventToActivityConverter {

    public Activity toActivity(DomainEvent event) {
        if (event instanceof BoardCreatedEvent) {
            return BoardActivities.from((BoardCreatedEvent) event);
        } else if (event instanceof BoardMemberAddedEvent) {
            return BoardActivities.from((BoardMemberAddedEvent) event);
        } else if (event instanceof CardAttachmentAddedEvent) {
            return CardActivities.from((CardAttachmentAddedEvent) event);
        } else if (event instanceof CardAddedEvent) {
            return CardActivities.from((CardAddedEvent) event);
        } else if (event instanceof CardDescriptionChangedEvent) {
            return CardActivities.from((CardDescriptionChangedEvent) event);
        } else if (event instanceof CardTitleChangedEvent) {
            return CardActivities.from((CardTitleChangedEvent) event);
        } else if (event instanceof CardListAddedEvent) {
            return CardListActivities.from((CardListAddedEvent) event);
        }
        log.debug("Event is null");
        return null;
    }
}
