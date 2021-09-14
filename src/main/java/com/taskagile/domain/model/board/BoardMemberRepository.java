package com.taskagile.domain.model.board;

import com.taskagile.domain.model.user.User;
import com.taskagile.domain.model.user.UserId;

import java.util.List;

public interface BoardMemberRepository {

    void save(BoardMember boardMember);

    List<User> findMembers(BoardId boardId);

    void add(BoardId boardId, UserId id);
}
