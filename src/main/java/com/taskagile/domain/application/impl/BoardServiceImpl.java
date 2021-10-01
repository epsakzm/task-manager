package com.taskagile.domain.application.impl;

import com.taskagile.domain.application.BoardService;
import com.taskagile.domain.application.commands.AddBoardMemberCommand;
import com.taskagile.domain.application.commands.CreateBoardCommand;
import com.taskagile.domain.common.event.DomainEventPublisher;
import com.taskagile.domain.model.board.*;
import com.taskagile.domain.model.board.events.BoardCreatedEvent;
import com.taskagile.domain.model.board.events.BoardMemberAddedEvent;
import com.taskagile.domain.model.user.User;
import com.taskagile.domain.model.user.UserFinder;
import com.taskagile.domain.model.user.UserId;
import com.taskagile.domain.model.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardManagement boardManagement;
    private final BoardMemberRepository boardMemberRepository;
    private final UserFinder userFinder;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public List<Board> findBoardsByMembership(UserId userId) {
        return boardRepository.findBoardsByMembership(userId);
    }

    @Override
    public Board createBoard(CreateBoardCommand command) {
        Board board = boardManagement.createBoard(command.getUserId(), command.getName(), command.getDescription(), command.getTeamId());
        domainEventPublisher.publish(new BoardCreatedEvent(board, command));
        return board;
    }

    @Override
    public User addMember(AddBoardMemberCommand command) throws UserNotFoundException {
        User user = userFinder.find(command.getUsernameOrEmailAddress());
        boardMemberRepository.add(command.getBoardId(), user.getId());
        domainEventPublisher.publish(new BoardMemberAddedEvent(command.getBoardId(), user, command));
        return user;
    }

    @Override
    public Board findById(BoardId boardId) {
        return boardRepository.findById(boardId);
    }

    @Override
    public List<User> findMembers(BoardId boardId) {
        return boardMemberRepository.findMembers(boardId);
    }

}
