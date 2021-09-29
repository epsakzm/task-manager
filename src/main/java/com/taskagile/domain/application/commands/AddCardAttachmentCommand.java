package com.taskagile.domain.application.commands;

import com.taskagile.domain.model.card.CardId;
import com.taskagile.domain.model.user.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class AddCardAttachmentCommand {

    private CardId cardId;
    private MultipartFile file;
    private UserId userId;

    public AddCardAttachmentCommand(long cardId, MultipartFile file, UserId userId) {
        this.cardId = new CardId(cardId);
        this.file = file;
        this.userId = userId;
    }
}
