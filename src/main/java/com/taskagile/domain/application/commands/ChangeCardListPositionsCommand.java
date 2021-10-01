package com.taskagile.domain.application.commands;

import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.cardlist.CardListPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ChangeCardListPositionsCommand extends UserCommand {

    private BoardId boardId;
    private List<CardListPosition> cardListPositions;
}
