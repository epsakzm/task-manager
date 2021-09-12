package com.taskagile.domain.model.board;

import com.taskagile.domain.common.model.AbstractBaseEntity;
import com.taskagile.domain.model.team.TeamId;
import com.taskagile.domain.model.user.UserId;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "board")
public class Board extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 128, unique = true)
    private String name;

    @Column(name = "description", nullable = false, length = 256, unique = true)
    private String description;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "archived")
    private boolean archived;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    public static Board create(UserId userId, String name, String description, TeamId teamId) {
        Board board = new Board();
        board.userId = userId.value();
        board.name = name;
        board.description = description;
        board.teamId = teamId.isValid() ? teamId.value() : null;
        board.archived = false;
        board.createdDate = new Date();
        return board;
    }

    public BoardId getId() {
        return new BoardId(id);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public long getUserId() {
        return userId;
    }

    public TeamId getTeamId() {
        return new TeamId(teamId);
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

        Board board = (Board) o;

        return new EqualsBuilder().append(userId, board.userId).append(name, board.name).append(teamId, board.teamId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(name).append(userId).append(teamId).toHashCode();
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                ", teamId=" + teamId +
                ", archived=" + archived +
                ", createdDate=" + createdDate +
                '}';
    }
}
