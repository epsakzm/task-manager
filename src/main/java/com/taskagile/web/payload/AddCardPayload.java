package com.taskagile.web.payload;

import com.taskagile.domain.application.commands.AddCardCommand;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.cardlist.CardListId;
import com.taskagile.domain.model.user.UserId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddCardPayload {

    private long boardId;
    private long cardListId;
    private String title;
    private int position;

    public AddCardCommand toCommand() {
        return new AddCardCommand(new CardListId(cardListId), title, position);
    }
}
