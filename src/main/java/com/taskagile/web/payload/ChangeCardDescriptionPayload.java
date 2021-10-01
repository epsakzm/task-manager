package com.taskagile.web.payload;

import com.taskagile.domain.application.commands.ChangeCardDescriptionCommand;
import com.taskagile.domain.model.card.CardId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangeCardDescriptionPayload {

    private String description;

    public ChangeCardDescriptionCommand toCommand(long cardId) {
        return new ChangeCardDescriptionCommand(new CardId(cardId), description);
    }
}
