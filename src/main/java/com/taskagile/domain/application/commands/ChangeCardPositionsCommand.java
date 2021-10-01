package com.taskagile.domain.application.commands;

import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.card.CardPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ChangeCardPositionsCommand extends UserCommand {

    private BoardId boardId;
    private List<CardPosition> cardPositions;
}
