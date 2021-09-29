package com.taskagile.domain.model.attachment;

import com.taskagile.domain.common.file.FileStorage;
import com.taskagile.domain.common.file.TempFile;
import com.taskagile.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Component
public class ThumbnailCreator {

    private final static Set<String> SUPPORTED_EXTENSIONS = new HashSet<>();
    private final static int MAX_WIDTH = 300;
    private final static int MAX_HEIGHT = 300;

    static {
        SUPPORTED_EXTENSIONS.add("png");
        SUPPORTED_EXTENSIONS.add("jpg");
        SUPPORTED_EXTENSIONS.add("jpeg");
    }

    private final ImageProcessor imageProcessor;

    public void create(FileStorage fileStorage, TempFile tempImageFile) {
        Assert.isTrue(tempImageFile.getFile().exists(), "Image file `" +
                tempImageFile.getFile().getAbsolutePath() + "` must exist");

        String ext = FilenameUtils.getExtension(tempImageFile.getFile().getName());
        if (!SUPPORTED_EXTENSIONS.contains(ext)) {
            throw new ThumbnailCreationException("Not supported image format for creating thumbnail");
        }

        log.debug("Creating thumbnail for file `{}`", tempImageFile.getFile().getName());

        try {
            String sourceFilePath = tempImageFile.getFile().getAbsolutePath();
            if (!sourceFilePath.endsWith("." + ext)) {
                throw new IllegalArgumentException("Image file's ext doesn't match the one in file descriptor");
            }
            String tempThumbnailFilePath = ImageUtils.getThumbnailVersion(tempImageFile.getFile().getAbsolutePath());
            Size resizeTo = getTargetSize(sourceFilePath);
            imageProcessor.resize(sourceFilePath, tempThumbnailFilePath, resizeTo);

            fileStorage.saveTempFile(TempFile.create(tempImageFile.tempRootPath(), Paths.get(tempThumbnailFilePath)));
            // Delete temp thumbnail file
            Files.delete(Paths.get(tempThumbnailFilePath));
        } catch (Exception e) {
            log.error("Failed to create thumbnail for file `" + tempImageFile.getFile().getAbsolutePath() + "`", e);
            throw new ThumbnailCreationException("Creating thumbnail failed", e);
        }
    }

    private Size getTargetSize(String imageFilePath) throws IOException {
        Size actualSize = imageProcessor.getSize(imageFilePath);
        if (actualSize.getHeight() <= MAX_HEIGHT && actualSize.getWidth() <= MAX_WIDTH) {
            return actualSize;
        }

        int width;
        int height;
        if (actualSize.getWidth() > actualSize.getHeight()) {
            width = MAX_WIDTH;
            height = (int) Math.floor(((double) width / (double) actualSize.getWidth()) * actualSize.getHeight());
        } else {
            height = MAX_HEIGHT;
            width = (int) Math.floor(((double) height / (double) actualSize.getHeight()) * actualSize.getWidth());
        }
        return new Size(width, height);
    }
}
