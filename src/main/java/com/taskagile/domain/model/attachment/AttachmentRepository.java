package com.taskagile.domain.model.attachment;

import com.taskagile.domain.model.card.CardId;

import java.util.List;

public interface AttachmentRepository {

    List<Attachment> findAttachments(CardId cardId);

    void save(Attachment attachment);

}
