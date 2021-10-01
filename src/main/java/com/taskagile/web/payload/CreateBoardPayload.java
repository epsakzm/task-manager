package com.taskagile.web.payload;

import com.taskagile.domain.application.commands.CreateBoardCommand;
import com.taskagile.domain.model.team.TeamId;
import com.taskagile.domain.model.user.UserId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateBoardPayload {

    private String name;
    private String description;
    private long teamId;

    public CreateBoardCommand toCommand(

    ) {
        return new CreateBoardCommand(name, description, new TeamId(teamId));
    }
}
