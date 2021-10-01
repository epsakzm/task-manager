package com.taskagile.domain.application.commands;

import com.taskagile.domain.model.card.CardId;
import com.taskagile.domain.model.user.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class AddCardAttachmentCommand extends UserCommand {

    private CardId cardId;
    private MultipartFile file;

    public AddCardAttachmentCommand(long cardId, MultipartFile file) {
        this.cardId = new CardId(cardId);
        this.file = file;
    }
}
