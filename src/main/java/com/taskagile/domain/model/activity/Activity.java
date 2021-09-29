package com.taskagile.domain.model.activity;

import com.taskagile.domain.common.model.AbstractBaseEntity;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.card.CardId;
import com.taskagile.domain.model.user.UserId;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "activity")
public class Activity extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private long userId;

    private Long cardId;

    private Long boardId;

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private ActivityType type;

    private String detail;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    public static Activity from(CardActivity cardActivity) {
        Activity activity = new Activity();
        activity.userId = cardActivity.getUserId().value();
        activity.cardId = cardActivity.getCardId().value();
        activity.boardId = cardActivity.getBoardId().value();
        activity.type = cardActivity.getType();
        activity.detail = cardActivity.getDetailJson();
        activity.createdDate = new Date();
        return activity;
    }

    public static Activity from(BoardActivity boardActivity) {
        Activity activity = new Activity();
        activity.userId = boardActivity.getUserId().value();
        activity.boardId = boardActivity.getBoardId().value();
        activity.type = boardActivity.getType();
        activity.detail = boardActivity.getDetailJson();
        activity.createdDate = new Date();
        return activity;
    }

    public ActivityId getId() {
        return new ActivityId(id);
    }

    public UserId getUserId() {
        return new UserId(userId);
    }

    public CardId getCardId() {
        return cardId == null ? null : new CardId(cardId);
    }

    public BoardId getBoardId() {
        return boardId == null ? null : new BoardId(boardId);
    }

    public ActivityType getType() {
        return type;
    }

    public String getDetail() {
        return detail;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;

        return new EqualsBuilder().append(userId, activity.userId).append(cardId, activity.cardId).append(boardId, activity.boardId).append(type, activity.type).append(detail, activity.detail).append(createdDate, activity.createdDate).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(userId).append(cardId).append(boardId).append(type).append(detail).append(createdDate).toHashCode();
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", userId=" + userId +
                ", cardId=" + cardId +
                ", boardId=" + boardId +
                ", type=" + type +
                ", detail='" + detail + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
