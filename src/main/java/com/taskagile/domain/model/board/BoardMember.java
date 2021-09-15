package com.taskagile.domain.model.board;

import com.taskagile.domain.common.model.AbstractBaseEntity;
import com.taskagile.domain.model.user.UserId;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "board_member")
public class BoardMember extends AbstractBaseEntity {

    @EmbeddedId
    private BoardMemberId id;

    public static BoardMember create(BoardId boardId, UserId userId) {
        BoardMember boardMember = new BoardMember();
        boardMember.id = new BoardMemberId(boardId, userId);
        return boardMember;
    }

    public BoardId getBoardId() {
        return id.getBoardId();
    }

    public UserId getUserId() {
        return id.getUserId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BoardMember that = (BoardMember) o;

        return new EqualsBuilder().append(id, that.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }

    @Override
    public String toString() {
        return "BoardMember{" +
                "id=" + id +
                '}';
    }

    @Embeddable
    @Access(AccessType.FIELD)
    public static class BoardMemberId implements Serializable {

        private long boardId;

        private long userId;

        public BoardMemberId() { }

        private BoardMemberId(BoardId boardId, UserId userId) {
            this.boardId = boardId.value();
            this.userId = userId.value();
        }

        public BoardId getBoardId() {
            return new BoardId(boardId);
        }

        public UserId getUserId() {
            return new UserId(userId);
        }

        @Override
        public String toString() {
            return "BoardMemberId{" +
                    "boardId=" + boardId +
                    ", userId=" + userId +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            BoardMemberId that = (BoardMemberId) o;

            return new EqualsBuilder().append(boardId, that.boardId).append(userId, that.userId).isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37).append(boardId).append(userId).toHashCode();
        }
    }
}
