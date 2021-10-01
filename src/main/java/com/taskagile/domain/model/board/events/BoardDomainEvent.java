package com.taskagile.domain.model.board.events;

import com.taskagile.domain.common.event.DomainEvent;
import com.taskagile.domain.common.event.TriggeredBy;
import com.taskagile.domain.model.board.BoardId;
import lombok.Getter;

@Getter
public abstract class BoardDomainEvent extends DomainEvent {

    private BoardId boardId;

    public BoardDomainEvent(BoardId boardId, TriggeredBy triggeredBy) {
        super(triggeredBy);
        this.boardId = boardId;
    }
}
