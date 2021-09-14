package com.taskagile.domain.application;

import com.taskagile.domain.application.commands.CreateBoardCommand;
import com.taskagile.domain.model.board.Board;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.user.User;
import com.taskagile.domain.model.user.UserId;
import com.taskagile.domain.model.user.UserNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface BoardService {

    List<Board> findBoardsByMembership(UserId userId);

    Board findById(BoardId boardId);

    List<User> findMembers(BoardId boardId);

    Board createBoard(CreateBoardCommand command);

    User addMember(BoardId boardId, String usernameOrEmailAddress) throws UserNotFoundException;
}
