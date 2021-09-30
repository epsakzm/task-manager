package com.taskagile.web.results;

import com.taskagile.domain.common.file.FileUrlCreator;
import com.taskagile.domain.model.attachment.Attachment;
import com.taskagile.utils.ImageUtils;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class AttachmentResults {

    public static ResponseEntity<ApiResult> build(List<Attachment> attachments, FileUrlCreator fileUrlCreator) {
        List<ListableAttachment> result = attachments.stream()
                .map(attachment -> new ListableAttachment(attachment, fileUrlCreator))
                .collect(Collectors.toList());

        ApiResult apiResult = ApiResult.blank()
                .add("attachments", result);

        return Result.ok(apiResult);
    }

    @Getter
    private static class ListableAttachment implements Serializable {

        private long id;
        private String fileName;
        private String fileType;
        private String fileUrl;
        private String previewUrl;
        private long userId;
        private long createdDate;

        ListableAttachment(Attachment attachment, FileUrlCreator fileUrlCreator) {
            this.id = attachment.getId().value();
            this.fileName = attachment.getFileName();
            this.fileType = attachment.getFileType();
            this.fileUrl = fileUrlCreator.url(attachment.getFilePath());
            if (attachment.isThumbnailCreated()) {
                this.previewUrl = ImageUtils.getThumbnailVersion(this.fileUrl);
            } else {
                this.previewUrl = "";
            }
            this.userId = attachment.getUserId().value();
            this.createdDate = attachment.getCreatedDate().getTime();
        }
    }
}
