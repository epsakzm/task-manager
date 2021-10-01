package com.taskagile.web.apis;

import com.taskagile.domain.application.BoardService;
import com.taskagile.domain.application.TeamService;
import com.taskagile.domain.application.UserService;
import com.taskagile.domain.common.security.CurrentUser;
import com.taskagile.domain.common.security.TokenManager;
import com.taskagile.domain.model.user.SimpleUser;
import com.taskagile.domain.model.user.User;
import com.taskagile.web.results.ApiResult;
import com.taskagile.web.results.MyDataResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class MeApiController {

    private final String realTimeServerUrl;
    private final TokenManager tokenManager;
    private final TeamService teamService;
    private final BoardService boardService;
    private final UserService userService;

    public MeApiController(@Value("${app.real-time-server-url}") String realTimeServerUrl,
                           TokenManager tokenManager,
                           TeamService teamService,
                           BoardService boardService,
                           UserService userService) {
        this.realTimeServerUrl = realTimeServerUrl;
        this.tokenManager = tokenManager;
        this.teamService = teamService;
        this.boardService = boardService;
        this.userService = userService;
    }

    @GetMapping("/api/me")
    public ResponseEntity<ApiResult> getMyData(@CurrentUser SimpleUser currentUser) {
        return MyDataResult.build(
                userService.findById(currentUser.getUserId()),
                teamService.findTeamsByUserId(currentUser.getUserId()),
                boardService.findBoardsByMembership(currentUser.getUserId()),
                realTimeServerUrl,
                tokenManager.jwt(currentUser.getUserId())
        );
    }
}
