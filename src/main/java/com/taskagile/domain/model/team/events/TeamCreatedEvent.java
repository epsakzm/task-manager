package com.taskagile.domain.model.team.events;

import com.taskagile.domain.common.event.DomainEvent;
import com.taskagile.domain.common.event.TriggeredBy;
import com.taskagile.domain.model.team.Team;
import com.taskagile.domain.model.team.TeamId;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class TeamCreatedEvent extends DomainEvent {

    private TeamId teamId;
    private String teamName;

    public TeamCreatedEvent(Team team, TriggeredBy triggeredBy) {
        super(triggeredBy);
        this.teamId = team.getId();
        this.teamName = team.getName();
    }
}
