package com.taskagile.domain.application.commands;

import com.taskagile.domain.model.board.BoardId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddBoardMemberCommand extends UserCommand {

    private BoardId boardId;
    private String usernameOrEmailAddress;

}
