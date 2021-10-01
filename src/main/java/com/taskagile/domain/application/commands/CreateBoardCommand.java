package com.taskagile.domain.application.commands;

import com.taskagile.domain.model.team.TeamId;
import com.taskagile.domain.model.user.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateBoardCommand extends UserCommand {

    private String name;
    private String description;
    private TeamId teamId;
}
