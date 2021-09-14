package com.taskagile.domain.application;

import com.taskagile.domain.application.commands.AddCardCommand;
import com.taskagile.domain.application.commands.ChangeCardPositionsCommand;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.card.Card;

import java.util.List;

public interface CardService {

    List<Card> findByBoardId(BoardId boardId);

    Card addCard(AddCardCommand command);

    void changePositions(ChangeCardPositionsCommand command);
}
