package com.taskagile.web.apis;

import com.taskagile.domain.application.TeamService;
import com.taskagile.domain.application.commands.CreateTeamCommand;
import com.taskagile.domain.common.security.CurrentUser;
import com.taskagile.domain.model.team.Team;
import com.taskagile.domain.model.user.SimpleUser;
import com.taskagile.web.payload.CreateTeamPayload;
import com.taskagile.web.results.ApiResult;
import com.taskagile.web.results.CreateTeamResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class TeamApiController extends AbstractBaseController {

    private final TeamService teamService;

    public ResponseEntity<ApiResult> createTeam(@RequestBody CreateTeamPayload payload,
                                                HttpServletRequest request) {
        CreateTeamCommand command = payload.toCommand();
        addTriggeredBy(command, request);
        Team team = teamService.createTeam(command);
        return CreateTeamResult.build(team);
    }
}
