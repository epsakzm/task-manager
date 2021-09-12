package com.taskagile.web.apis;

import com.taskagile.domain.application.TeamService;
import com.taskagile.domain.common.security.CurrentUser;
import com.taskagile.domain.model.user.SimpleUser;
import com.taskagile.web.payload.CreateTeamPayload;
import com.taskagile.web.results.ApiResult;
import com.taskagile.web.results.CreateTeamResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class TeamApiController {

    private final TeamService teamService;

    public TeamApiController(TeamService teamService) {
        this.teamService = teamService;
    }

    public ResponseEntity<ApiResult> createTeam(@RequestBody CreateTeamPayload payload,
                                                @CurrentUser SimpleUser currentUser) {
        return CreateTeamResult.build(teamService.createTeam(payload.toCommand(currentUser.getUserId())));
    }
}
