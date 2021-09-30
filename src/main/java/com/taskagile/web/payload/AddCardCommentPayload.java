package com.taskagile.web.payload;

import com.taskagile.domain.application.commands.AddCardCommentCommand;
import com.taskagile.domain.model.card.CardId;
import com.taskagile.domain.model.user.UserId;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddCardCommentPayload {

    private String comment;

    public AddCardCommentCommand toCommand(long cardId, long userId) {
        return new AddCardCommentCommand(new UserId(userId), new CardId(cardId), comment);
    }
}
