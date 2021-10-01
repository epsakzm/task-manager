package com.taskagile.web.payload;

import com.taskagile.domain.application.commands.CreateTeamCommand;
import com.taskagile.domain.model.user.UserId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateTeamPayload {

    private String name;

    public CreateTeamCommand toCommand() {
        return new CreateTeamCommand(name);
    }
}
