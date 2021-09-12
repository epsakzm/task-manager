package com.taskagile.web.results;

import com.taskagile.domain.model.board.Board;
import org.springframework.http.ResponseEntity;

import static com.taskagile.web.results.Result.*;

public class CreateBoardResult {

    public static ResponseEntity<ApiResult> build(Board board) {
        return Result.ok(ApiResult.blank()
            .add(PAYLOAD_ID, board.getId())
            .add(PAYLOAD_NAME, board.getName())
            .add(PAYLOAD_DESCRIPTION, board.getDescription())
            .add(PAYLOAD_TEAM_ID, board.getTeamId()));
    }
}
