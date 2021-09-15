package com.taskagile.web.results;

import com.taskagile.domain.model.board.Board;
import com.taskagile.domain.model.team.Team;
import com.taskagile.domain.model.user.SimpleUser;
import com.taskagile.domain.model.user.User;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyDataResult {

    public static ResponseEntity<ApiResult> build(User user,
                                                  List<Team> teams,
                                                  List<Board> boards,
                                                  String realTimeServerUrl,
                                                  String realTimeToken) {

        Map<String, Object> userData = new HashMap<>();
        userData.put("name", user.getFirstName() + " " + user.getLastName());
        userData.put("token", realTimeToken);

        Map<String, Object> settings = new HashMap<>();
        settings.put("realTimeServerUrl", realTimeServerUrl);

        return Result.ok(ApiResult.blank()
            .add("user", userData)
            .add("teams", teams.stream().map(TeamResult::new).collect(Collectors.toList()))
            .add("boards", boards.stream().map(BoardResult::new).collect(Collectors.toList()))
            .add("settings", settings));
    }

    private static class TeamResult {
        private final long id;
        private final String name;

        TeamResult(Team team) {
            this.id = team.getId().value();
            this.name = team.getName();
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    private static class BoardResult {
        private final long id;
        private final String name;
        private final String description;
        private final long teamId;

        BoardResult(Board board) {
            this.id = board.getId().value();
            this.name = board.getName();
            this.description = board.getDescription();
            this.teamId = board.getTeamId().value();
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public long getTeamId() {
            return teamId;
        }
    }
}
