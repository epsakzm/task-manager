package com.taskagile.web.payload;

import com.taskagile.domain.application.commands.AddCardCommentCommand;
import com.taskagile.domain.model.card.CardId;
import com.taskagile.domain.model.user.UserId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AddCardCommentPayload {

    private String comment;

    public AddCardCommentCommand toCommand(CardId cardId) {
        return new AddCardCommentCommand(cardId, comment);
    }
}
