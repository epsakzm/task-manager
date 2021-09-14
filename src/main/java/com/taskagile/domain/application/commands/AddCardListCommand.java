package com.taskagile.domain.application.commands;

import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.user.UserId;

public class AddCardListCommand {

    private UserId userId;
    private String name;
    private BoardId boardId;
    private int position;

    public UserId getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public BoardId getBoardId() {
        return boardId;
    }

    public int getPosition() {
        return position;
    }

    public AddCardListCommand(UserId userId, String name, BoardId boardId, int position) {
        this.userId = userId;
        this.name = name;
        this.boardId = boardId;
        this.position = position;
    }
}
