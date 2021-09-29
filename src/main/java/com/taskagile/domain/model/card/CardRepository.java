package com.taskagile.domain.model.card;

import com.taskagile.domain.model.board.BoardId;

import java.util.List;

public interface CardRepository {

    List<Card> findByBoardId(BoardId boardId);

    void save(Card card);

    void changePositions(List<CardPosition> cardPositions);

    Card findById(CardId cardId);
}
