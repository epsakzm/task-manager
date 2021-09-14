package com.taskagile.domain.model.cardlist;

import com.taskagile.domain.model.board.BoardId;

import java.util.List;

public interface CardListRepository {
    List<CardList> findByBoardId(BoardId boardId);

    void save(CardList cardList);

    void changePositions(List<CardListPosition> cardListPositions);
}
