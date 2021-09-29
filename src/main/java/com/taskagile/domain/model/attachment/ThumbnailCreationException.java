package com.taskagile.domain.model.attachment;

public class ThumbnailCreationException extends RuntimeException {

    public ThumbnailCreationException(String message) {
        super(message);
    }

    public ThumbnailCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
