package com.taskagile.domain.application.impl;

import com.taskagile.domain.application.CardService;
import com.taskagile.domain.application.commands.*;
import com.taskagile.domain.common.event.DomainEventPublisher;
import com.taskagile.domain.model.activity.Activity;
import com.taskagile.domain.model.activity.ActivityRepository;
import com.taskagile.domain.model.activity.CardActivities;
import com.taskagile.domain.model.attachment.Attachment;
import com.taskagile.domain.model.attachment.AttachmentManagement;
import com.taskagile.domain.model.attachment.AttachmentRepository;
import com.taskagile.domain.model.attachment.events.CardAttachmentAddedEvent;
import com.taskagile.domain.model.board.BoardId;
import com.taskagile.domain.model.card.Card;
import com.taskagile.domain.model.card.CardId;
import com.taskagile.domain.model.card.CardRepository;
import com.taskagile.domain.model.card.events.CardAddedEvent;
import com.taskagile.domain.model.card.events.CardDescriptionChangedEvent;
import com.taskagile.domain.model.card.events.CardTitleChangedEvent;
import com.taskagile.domain.model.cardlist.CardList;
import com.taskagile.domain.model.cardlist.CardListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CardListRepository cardListRepository;
    private final ActivityRepository activityRepository;
    private final AttachmentManagement attachmentManagement;
    private final AttachmentRepository attachmentRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public List<Card> findByBoardId(BoardId boardId) {
        return cardRepository.findByBoardId(boardId);
    }

    @Override
    public Card addCard(AddCardCommand command) {
        CardList cardList = cardListRepository.findById(command.getCardListId());
        Assert.notNull(cardList, "Card list must not be null");

        Card card = Card.create(cardList, command.getUserId(), command.getTitle(), command.getPosition());
        cardRepository.save(card);
        domainEventPublisher.publish(new CardAddedEvent(card, command));
        return card;
    }

    @Override
    public void changePositions(ChangeCardPositionsCommand command) {
        cardRepository.changePositions(command.getCardPositions());
    }

    @Override
    public Card findById(CardId cardId) {
        return cardRepository.findById(cardId);
    }

    @Override
    public List<Activity> findCardActivities(CardId cardId) {
        return activityRepository.findCardActivities(cardId);
    }

    @Override
    public List<Attachment> getAttachments(CardId cardId) {
        return attachmentRepository.findAttachments(cardId);
    }

    @Override
    public void changeCardTitle(ChangeCardTitleCommand command) {
        Card card = cardRepository.findById(command.getCardId());
        String beforeTitle = card.getTitle();
        card.changeTitle(command.getTitle());
        cardRepository.save(card);
        domainEventPublisher.publish(new CardTitleChangedEvent(card, beforeTitle, command));
    }

    @Override
    public void changeCardDescription(ChangeCardDescriptionCommand command) {
        Card card = cardRepository.findById(command.getCardId());
        String beforeDescription = card.getDescription();
        card.changeDescription(command.getDescription());
        cardRepository.save(card);
        domainEventPublisher.publish(new CardDescriptionChangedEvent(card, beforeDescription, command));
    }

    @Override
    public Activity addComment(AddCardCommentCommand command) {
        Card card = cardRepository.findById(command.getCardId());
        // CardActivities.from
        Activity cardActivity = CardActivities.from(card, command.getUserId(), command.getComment(), command.getIpAddress());
        activityRepository.save(cardActivity);
        return cardActivity;
    }

    @Override
    public Attachment addAttachment(AddCardAttachmentCommand command) {
        Card card = cardRepository.findById(command.getCardId());

        Attachment attachment = attachmentManagement.save(
                command.getCardId(), command.getFile(), command.getUserId());
        if (!card.hasCoverImage() && attachment.isThumbnailCreated()) {
            card.addCoverImage(attachment.getFilePath());
            cardRepository.save(card);
        }
        domainEventPublisher.publish(new CardAttachmentAddedEvent(card, attachment, command));
        return attachment;
    }
}
