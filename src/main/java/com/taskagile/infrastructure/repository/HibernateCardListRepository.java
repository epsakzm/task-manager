package com.taskagile.infrastructure.repository;

import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.cardlist.CardList;
import com.taskagile.domain.model.cardlist.CardListId;
import com.taskagile.domain.model.cardlist.CardListPosition;
import com.taskagile.domain.model.cardlist.CardListRepository;
import org.hibernate.query.NativeQuery;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class HibernateCardListRepository extends HibernateSupport<CardList> implements CardListRepository {

    private JdbcTemplate jdbcTemplate;

    public HibernateCardListRepository(EntityManager entityManager, JdbcTemplate jdbcTemplate) {
        super(entityManager);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CardList> findByBoardId(BoardId boardId) {
        String sql = "SELECT cl.* FROM card_list cl WHERE cl.board_id = :boardId";
        NativeQuery<CardList> query = getSession().createNativeQuery(sql, CardList.class);
        query.setParameter("boardId", boardId.value());
        return query.list();
    }

    @Override
    public void changePositions(List<CardListPosition> cardListPositions) {
        String sql = "UPDATE card_list SET `position` = ? WHERE id = ?";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                CardListPosition cardListPosition = cardListPositions.get(i);
                preparedStatement.setInt(1, cardListPosition.getPosition());
                preparedStatement.setLong(2, cardListPosition.getCardListId().value());
            }

            @Override
            public int getBatchSize() {
                return cardListPositions.size();
            }
        });
    }

    @Override
    public CardList findById(CardListId cardListId) {
        return getSession().find(CardList.class, cardListId.value());
    }
}
