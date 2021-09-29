package com.taskagile.domain.model.card;

import com.taskagile.domain.common.model.AbstractBaseEntity;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.cardlist.CardList;
import com.taskagile.domain.model.cardlist.CardListId;
import com.taskagile.domain.model.user.UserId;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "card")
public class Card extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_id")
    private long boardId;

    @Column(name = "card_list_id")
    private long cardListId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "position")
    private int position;

    @Column(name = "archived")
    private boolean archived;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    public static Card create(CardList cardList, UserId userId, String title, int position) {
        Card card = new Card();
        card.boardId = cardList.getBoardId().value();
        card.cardListId = cardList.getId().value();
        card.userId = userId.value();
        card.title = title;
        card.position = position;
        card.archived = false;
        card.description = "";
        card.createdDate = new Date();
        return card;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public BoardId getBoardId() {
        return new BoardId(boardId);
    }

    public CardId getId() {
        return new CardId(id);
    }

    public CardListId getCardListId() {
        return new CardListId(cardListId);
    }

    public UserId getUserId() {
        return new UserId(userId);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
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

        Card card = (Card) o;

        return new EqualsBuilder().append(cardListId, card.cardListId).append(userId, card.userId).append(position, card.position).append(archived, card.archived).append(title, card.title).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(cardListId).append(userId).append(title).append(position).append(archived).toHashCode();
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", boardId=" + boardId +
                ", cardListId=" + cardListId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", position=" + position +
                ", archived=" + archived +
                ", createdDate=" + createdDate +
                '}';
    }
}
