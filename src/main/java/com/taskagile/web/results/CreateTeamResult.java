package com.taskagile.web.results;

import com.taskagile.domain.model.team.Team;
import org.springframework.http.ResponseEntity;

import static com.taskagile.web.results.Result.PAYLOAD_ID;
import static com.taskagile.web.results.Result.PAYLOAD_NAME;

public class CreateTeamResult {

    public static ResponseEntity<ApiResult> build(Team team) {
        return Result.ok(ApiResult.blank()
            .add(PAYLOAD_ID, team.getId())
            .add(PAYLOAD_NAME, team.getName()));
    }
}
