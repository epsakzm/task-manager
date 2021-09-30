package com.taskagile.domain.model.attachment;

import com.taskagile.domain.common.model.AbstractBaseEntity;
import com.taskagile.domain.model.card.CardId;
import com.taskagile.domain.model.user.UserId;
import lombok.Getter;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@Table(name = "attachment")
public class Attachment extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_id")
    private long cardId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "thumbnail_created")
    private boolean thumbnailCreated;

    @Column(name = "archived")
    private boolean archived;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    public static Attachment create(CardId cardId, UserId userId, String fileName, String filePath, boolean thumbnailCreated) {
        Attachment attachment = new Attachment();
        attachment.cardId = cardId.value();
        attachment.userId = userId.value();
        attachment.fileName = fileName;
        attachment.fileType = FilenameUtils.getExtension(fileName);
        attachment.filePath = filePath;
        attachment.thumbnailCreated = thumbnailCreated;
        attachment.archived = false;
        attachment.createdDate = new Date();
        return attachment;
    }

    public AttachmentId getId() {
        return new AttachmentId(id);
    }

    public CardId getCardId() {
        return new CardId(cardId);
    }

    public UserId getUserId() {
        return new UserId(userId);
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public boolean isArchived() {
        return archived;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Attachment that = (Attachment) o;

        return new EqualsBuilder().append(cardId, that.cardId).append(userId, that.userId).append(archived, that.archived).append(fileType, that.fileType).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(cardId).append(userId).append(fileType).append(archived).toHashCode();
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", cardId=" + cardId +
                ", userId=" + userId +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileType='" + fileType + '\'' +
                ", thumbnailCreated=" + thumbnailCreated +
                ", archived=" + archived +
                ", createdDate=" + createdDate +
                '}';
    }
}
