package com.taskagile.domain.model.activity;

import com.taskagile.domain.common.model.AbstractBaseEntity;
import com.taskagile.domain.common.model.IpAddress;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.card.CardId;
import com.taskagile.domain.model.user.UserId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;

@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@Entity
@Table(name = "activity")
public class Activity extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "card_id")
    private Long cardId;

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private ActivityType type;

    @Column(name = "detail")
    private String detail;

    @Column(name = "ip_address")
    private String ipAddress;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    private Activity(UserId userId, @Nullable CardId cardId, BoardId boardId, ActivityType type, String detail, IpAddress ipAddress) {
        this.userId = userId.value();
        this.cardId = cardId != null ? cardId.value() : null;
        this.boardId = boardId.value();
        this.type = type;
        this.detail = detail;
        this.ipAddress = ipAddress.value();
        this.createdDate = new Date();
    }

    public static Activity from(UserId userId, BoardId boardId, ActivityType type, String detail, IpAddress ipAddress) {
        return new Activity(userId, null, boardId, type, detail, ipAddress);
    }

    public static Activity from(UserId userId, CardId cardId, BoardId boardId, ActivityType type, String detail, IpAddress ipAddress) {
        return new Activity(userId, cardId, boardId, type, detail, ipAddress);
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

}
