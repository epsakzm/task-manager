package com.taskagile.domain.common.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileUrlCreator {

    private boolean isLocalStorage;
    private String cdnUrl;

    public FileUrlCreator(@Value("${app.file-storage.active}") String fileStorage,
                          @Value("${app.cdn.url}") String cdnUrl) {
        this.isLocalStorage = "localFileStorage".equals(fileStorage);
        this.cdnUrl = cdnUrl;
    }

    public String url(String fileRelativePath) {
        if (fileRelativePath == null) {
            return null;
        }

        if (fileRelativePath.startsWith("https://") || fileRelativePath.startsWith("http://")) {
            return fileRelativePath;
        }

        if (isLocalStorage) {
            return "/local-file/" + fileRelativePath;
        }
        return cdnUrl + "/" + fileRelativePath;
    }
}
