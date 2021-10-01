package com.taskagile.web.payload;

import com.taskagile.domain.application.commands.AddBoardMemberCommand;
import com.taskagile.domain.model.board.BoardId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddBoardMemberPayload {

    private String usernameOrEmailAddress;

    public AddBoardMemberCommand toCommand(BoardId boardId) {
        return new AddBoardMemberCommand(boardId, usernameOrEmailAddress);
    }
}
