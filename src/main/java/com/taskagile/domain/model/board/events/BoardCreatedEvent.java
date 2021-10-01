package com.taskagile.domain.model.board.events;

import com.taskagile.domain.common.event.DomainEvent;
import com.taskagile.domain.common.event.TriggeredBy;
import com.taskagile.domain.model.board.Board;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class BoardCreatedEvent extends BoardDomainEvent {

    private String boardName;

    public BoardCreatedEvent(Board board, TriggeredBy triggeredBy) {
        super(board.getId(), triggeredBy);
    }
}
