package com.taskagile.web.payload;

import com.taskagile.domain.application.commands.AddCardListCommand;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.user.UserId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddCardListPayload {

    private long boardId;
    private String name;
    private int position;

    public AddCardListCommand toCommand() {
        return new AddCardListCommand(new BoardId(boardId), name, position);
    }
}
