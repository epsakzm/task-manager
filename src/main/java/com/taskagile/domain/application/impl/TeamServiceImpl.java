package com.taskagile.domain.application.impl;

import com.taskagile.domain.application.TeamService;
import com.taskagile.domain.application.commands.CreateTeamCommand;
import com.taskagile.domain.common.event.DomainEventPublisher;
import com.taskagile.domain.model.team.Team;
import com.taskagile.domain.model.team.TeamRepository;
import com.taskagile.domain.model.team.events.TeamCreatedEvent;
import com.taskagile.domain.model.user.UserId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;
    private DomainEventPublisher domainEventPublisher;

    public TeamServiceImpl(TeamRepository teamRepository, DomainEventPublisher domainEventPublisher) {
        this.teamRepository = teamRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Team createTeam(CreateTeamCommand command) {
        Team team = Team.create(command.getName(), command.getUserId());
        domainEventPublisher.publish(new TeamCreatedEvent(this, team));
        return team;
    }

    @Override
    public List<Team> findTeamsByUserId(UserId userId) {
        return teamRepository.findTeamsByUserId(userId);
    }
}
