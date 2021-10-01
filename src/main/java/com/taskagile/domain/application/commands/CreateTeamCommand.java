package com.taskagile.domain.application.commands;

import com.taskagile.domain.model.user.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateTeamCommand extends UserCommand {

    private String name;
}
