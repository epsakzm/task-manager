package com.taskagile.domain.application.commands;

import com.taskagile.domain.model.cardlist.CardListId;
import com.taskagile.domain.model.user.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AddCardCommand extends UserCommand {

    private CardListId cardListId;
    private String title;
    private int position;
}
