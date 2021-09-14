package com.taskagile.domain.model.cardlist;

import com.taskagile.domain.common.model.AbstractBaseEntity;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.user.UserId;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "card_list")
public class CardList extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_id")
    private long boardId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "position")
    private int position;

    @Column(name = "archived")
    private boolean archived;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    public static CardList create(BoardId boardId, UserId userId, String name, int position) {
        CardList cardList = new CardList();
        cardList.boardId = boardId.value();
        cardList.userId = userId.value();
        cardList.name = name;
        cardList.position = position;
        cardList.archived = false;
        cardList.createdDate = new Date();
        return cardList;
    }

    public CardListId getId() {
        return new CardListId(id);
    }

    public UserId getUserId() {
        return new UserId(userId);
    }

    public BoardId getBoardId() {
        return new BoardId(boardId);
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public boolean isArchived() {
        return archived;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CardList cardList = (CardList) o;

        return new EqualsBuilder().append(boardId, cardList.boardId).append(position, cardList.position).append(archived, cardList.archived).append(name, cardList.name).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(boardId).append(name).append(position).append(archived).toHashCode();
    }

    @Override
    public String toString() {
        return "CardList{" +
                "id=" + id +
                ", boardId=" + boardId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", position=" + position +
                ", archived=" + archived +
                ", createdDate=" + createdDate +
                '}';
    }
}
