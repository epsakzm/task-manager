package com.taskagile.infrastructure.file.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.taskagile.domain.common.file.AbstractBaseFileStorage;
import com.taskagile.domain.common.file.TempFile;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component("s3FileStorage")
public class S3FileStorage extends AbstractBaseFileStorage {

    private Environment environment;
    private String rootTempPath;
    private AmazonS3 s3;

    public S3FileStorage(Environment environment,
                         @Value("${app.file-storage.temp-folder}") String rootTempPath) {
        this.environment = environment;
        this.rootTempPath = rootTempPath;
        if (environment.getProperty("app.file-storage.active").equals("s3FileStorage")) {
            this.s3 = initS3Client();
        }
    }

    private AmazonS3 initS3Client() {
        String s3Region = environment.getProperty("app.file-storage.s3-region");
        Assert.hasText(s3Region);

        if (environment.acceptsProfiles("dev")) {
            log.debug("initialize S3 client");
            String s3AccessKey = environment.getProperty("app.file-storage.s3-access-key");
            String s3SecretKey = environment.getProperty("app.file-storage.s3-secret-key");

            Assert.hasText(s3AccessKey);
            Assert.hasText(s3SecretKey);

            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(s3AccessKey, s3SecretKey);
            AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);

            AmazonS3ClientBuilder s3ClientBuilder = AmazonS3ClientBuilder.standard();
            s3ClientBuilder.setRegion(s3Region);
            s3ClientBuilder.withCredentials(credentialsProvider);
            return s3ClientBuilder.build();
        } else {
            log.debug("S3 with IAM role");
            return AmazonS3ClientBuilder.standard()
                    .withCredentials(new InstanceProfileCredentialsProvider(false))
                    .withRegion(s3Region)
                    .build();
        }
    }

    @Override
    public TempFile saveAsTempFile(String folder, MultipartFile file) {
        return null;
    }

    @Override
    public void saveTempFile(TempFile tempFile) {

    }

    @Override
    public String saveUploaded(String folder, MultipartFile file) {
        return null;
    }
}
