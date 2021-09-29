package com.taskagile.domain.model.attachment;

import com.taskagile.domain.common.file.FileStorage;
import com.taskagile.domain.common.file.FileStorageResolver;
import com.taskagile.domain.common.file.TempFile;
import com.taskagile.domain.model.card.CardId;
import com.taskagile.domain.model.user.UserId;
import com.taskagile.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;

@RequiredArgsConstructor
@Slf4j
@Component
public class AttachmentManagement {

    private final FileStorageResolver fileStorageResolver;
    private final ThumbnailCreator thumbnailCreator;
    private final AttachmentRepository attachmentRepository;

    public Attachment save(CardId cardId, MultipartFile file, UserId userId) {
        FileStorage fileStorage = fileStorageResolver.resolve();

        String filePath;
        String folder = "attachments";
        boolean thumbnailCreated = false;
        if (ImageUtils.isImage(file.getContentType())) {
            filePath = saveImage(fileStorage, folder, file);
            thumbnailCreated = true;
        } else {
            filePath = fileStorage.saveUploaded(folder, file);
        }

        Attachment attachment = Attachment.create(cardId, userId, file.getOriginalFilename(), filePath, thumbnailCreated);
        attachmentRepository.save(attachment);
        return attachment;
    }

    private String saveImage(FileStorage fileStorage, String folder, MultipartFile file) {
        TempFile tempImageFile = fileStorage.saveAsTempFile(folder, file);

        fileStorage.saveTempFile(tempImageFile);

        thumbnailCreator.create(fileStorage, tempImageFile);

        try {
            Files.delete(tempImageFile.getFile().toPath());
        } catch (IOException e) {
            log.error("Failed to delete temp file `" + tempImageFile.getFile().getAbsolutePath() + "`", e);
        }
        return tempImageFile.getFileRelativePath();
    }
}
