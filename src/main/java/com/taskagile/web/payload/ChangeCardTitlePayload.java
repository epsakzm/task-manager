package com.taskagile.web.payload;

import com.taskagile.domain.application.commands.ChangeCardTitleCommand;
import com.taskagile.domain.model.card.CardId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangeCardTitlePayload {

    private String title;

    public ChangeCardTitleCommand toCommand(long cardId) {
        return new ChangeCardTitleCommand(new CardId(cardId), title);
    }
}
