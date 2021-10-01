package com.taskagile.domain.application.commands;

import com.taskagile.domain.model.card.CardId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangeCardDescriptionCommand extends UserCommand {

    private CardId cardId;
    private String description;
}
