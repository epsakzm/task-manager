package com.taskagile.domain.application;

import com.taskagile.domain.application.commands.*;
import com.taskagile.domain.model.activity.Activity;
import com.taskagile.domain.model.attachment.Attachment;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.card.Card;
import com.taskagile.domain.model.card.CardId;

import java.util.List;

public interface CardService {

    List<Card> findByBoardId(BoardId boardId);

    Card addCard(AddCardCommand command);

    Card findById(CardId cardId);

    List<Activity> findCardActivities(CardId cardId);

    List<Attachment> getAttachments(CardId cardId);

    void changeCardTitle(ChangeCardTitleCommand command);

    void changeCardDescription(ChangeCardDescriptionCommand command);

    Activity addComment(AddCardCommentCommand command);

    Attachment addAttachment(AddCardAttachmentCommand command);

    void changePositions(ChangeCardPositionsCommand command);
}
