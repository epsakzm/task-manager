package com.taskagile.web.apis;

import com.taskagile.domain.application.*;
import com.taskagile.domain.application.commands.AddBoardMemberCommand;
import com.taskagile.domain.application.commands.CreateBoardCommand;
import com.taskagile.domain.common.file.FileUrlCreator;
import com.taskagile.domain.common.security.CurrentUser;
import com.taskagile.domain.model.board.Board;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.card.Card;
import com.taskagile.domain.model.cardlist.CardList;
import com.taskagile.domain.model.team.Team;
import com.taskagile.domain.model.user.SimpleUser;
import com.taskagile.domain.model.user.User;
import com.taskagile.domain.model.user.UserNotFoundException;
import com.taskagile.web.payload.AddBoardMemberPayload;
import com.taskagile.web.payload.CreateBoardPayload;
import com.taskagile.web.results.ApiResult;
import com.taskagile.web.results.BoardResult;
import com.taskagile.web.results.CreateBoardResult;
import com.taskagile.web.results.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardApiController extends AbstractBaseController {

    private final BoardService boardService;
    private final TeamService teamService;
    private final CardListService cardListService;
    private final CardService cardService;
    private final FileUrlCreator fileUrlCreator;

    @PostMapping("/api/boards")
    public ResponseEntity<ApiResult> createBoard(@RequestBody CreateBoardPayload payload, HttpServletRequest request) {
        CreateBoardCommand command = payload.toCommand();
        addTriggeredBy(command, request);
        Board board = boardService.createBoard(command);
        return CreateBoardResult.build(board);
    }

    @GetMapping("/api/boards/{boardId}")
    public ResponseEntity<ApiResult> getBoard(@PathVariable("boardId") long rawBoardId) {
        BoardId boardId = new BoardId(rawBoardId);
        Board board = boardService.findById(boardId);
        if (board == null) {
            return Result.notFound();
        }

        List<User> members = boardService.findMembers(boardId);

        Team team = null;
        if (!board.isPersonal()) {
            team = teamService.findById(board.getTeamId());
        }

        List<CardList> cardLists = cardListService.findByBoardId(boardId);
        List<Card> cards = cardService.findByBoardId(boardId);

        return BoardResult.build(team, board, members, cardLists, cards, fileUrlCreator);
    }

    @PostMapping("/api/boards/{boardId}/members")
    public ResponseEntity<ApiResult> addMember(@PathVariable("boardId") long rawBoardId,
                                               @RequestBody AddBoardMemberPayload payload,
                                               HttpServletRequest request) {
        BoardId boardId = new BoardId(rawBoardId);
        Board board = boardService.findById(boardId);
        if (board == null) {
            return Result.notFound();
        }

        try {
            AddBoardMemberCommand command = payload.toCommand(boardId);
            addTriggeredBy(command, request);
            User member = boardService.addMember(command);

            ApiResult result = ApiResult.blank()
                    .add("id", member.getId().value())
                    .add("shortName", member.getInitials());
            return Result.ok(result);
        } catch (UserNotFoundException e) {
            return Result.failure("No User Found");
        }
    }
}
