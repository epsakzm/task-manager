package com.taskagile.domain.application.commands;

import com.taskagile.domain.model.card.CardId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangeCardTitleCommand {

    private CardId cardId;
    private String title;

}
