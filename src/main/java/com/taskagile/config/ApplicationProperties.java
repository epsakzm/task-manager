package com.taskagile.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Configuration
@ConfigurationProperties(prefix = "app")
@Validated
public class ApplicationProperties {

    @Email
    @NotBlank
    private String mailFrom;

    @NotBlank
    private String tokenSecretKey;

    @NotBlank
    private String realTimeServerUrl;

    @NotNull
    private FileStorageSetting fileStorage;

    public FileStorageSetting getFileStorage() {
        return fileStorage;
    }

    public void setFileStorage(FileStorageSetting fileStorage) {
        this.fileStorage = fileStorage;
    }

    public ImageSetting getImage() {
        return image;
    }

    public void setImage(ImageSetting image) {
        this.image = image;
    }

    @NotNull
    private ImageSetting image;

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getTokenSecretKey() {
        return tokenSecretKey;
    }

    public void setTokenSecretKey(String tokenSecretKey) {
        this.tokenSecretKey = tokenSecretKey;
    }

    public String getRealTimeServerUrl() {
        return realTimeServerUrl;
    }

    public void setRealTimeServerUrl(String realTimeServerUrl) {
        this.realTimeServerUrl = realTimeServerUrl;
    }

    @Getter
    @Setter
    private static class FileStorageSetting {
        private String localRootFolder;

        @NotBlank
        @NotEmpty
        private String tempFolder;

        @NotBlank
        @NotEmpty
        private String active;

        private String s3AccessKey;
        private String s3SecretKey;
        private String s3BucketName;
        private String s3Region;
    }

    @Getter
    @Setter
    private static class ImageSetting {
        @NotBlank
        @NotEmpty
        private String commandSearchPath;
    }
}
