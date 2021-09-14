package com.taskagile.web.apis;

import com.taskagile.domain.application.BoardService;
import com.taskagile.domain.application.TeamService;
import com.taskagile.domain.application.UserService;
import com.taskagile.domain.common.security.CurrentUser;
import com.taskagile.domain.model.user.SimpleUser;
import com.taskagile.domain.model.user.User;
import com.taskagile.web.results.ApiResult;
import com.taskagile.web.results.MyDataResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class MeApiController {

    private final TeamService teamService;
    private final BoardService boardService;
    private final UserService userService;

    public MeApiController(TeamService teamService, BoardService boardService, UserService userService) {
        this.teamService = teamService;
        this.boardService = boardService;
        this.userService = userService;
    }

    @GetMapping("/api/me")
    public ResponseEntity<ApiResult> getMyData(@CurrentUser SimpleUser currentUser) {
        User user = userService.findById(currentUser.getUserId());
        return MyDataResult.build(
                user,
                teamService.findTeamsByUserId(currentUser.getUserId()),
                boardService.findBoardsByMembership(currentUser.getUserId())
        );
    }
}
