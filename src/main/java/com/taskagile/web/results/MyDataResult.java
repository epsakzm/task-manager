package com.taskagile.web.results;

import com.taskagile.domain.model.board.Board;
import com.taskagile.domain.model.team.Team;
import com.taskagile.domain.model.user.SimpleUser;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

public class MyDataResult {

    public static ResponseEntity<ApiResult> build(SimpleUser currentUser, List<Team> teams, List<Board> boards) {
        return Result.ok(ApiResult.blank()
            .add("user", ApiResult.of("name", currentUser.getUsername()))
            .add("teams", teams.stream().map(TeamResult::new).collect(Collectors.toList()))
            .add("boards", boards.stream().map(BoardResult::new).collect(Collectors.toList())));
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
