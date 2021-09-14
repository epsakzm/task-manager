package com.taskagile.web.payload;

import com.taskagile.domain.application.commands.AddCardListCommand;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.user.UserId;

public class AddCardListPayload {

    private long boardId;
    private String name;
    private int position;

    public AddCardListCommand toCommand(UserId userId) {
        return new AddCardListCommand(userId, name, new BoardId(boardId), position);
    }

    public BoardId getBoardId() {
        return new BoardId(boardId);
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
