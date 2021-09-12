package com.taskagile.web.apis;

import com.taskagile.domain.application.BoardService;
import com.taskagile.domain.application.TeamService;
import com.taskagile.domain.common.security.CurrentUser;
import com.taskagile.domain.model.user.SimpleUser;
import com.taskagile.web.results.ApiResult;
import com.taskagile.web.results.MyDataResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class MeApiController {

    private final TeamService teamService;
    private final BoardService boardService;

    public MeApiController(TeamService teamService, BoardService boardService) {
        this.teamService = teamService;
        this.boardService = boardService;
    }

    @GetMapping("/api/me")
    public ResponseEntity<ApiResult> getMyData(@CurrentUser SimpleUser currentUser) {
        return MyDataResult.build(
                currentUser,
                teamService.findTeamsByUserId(currentUser.getUserId()),
                boardService.findBoardsByMembership(currentUser.getUserId())
        );
    }
}
