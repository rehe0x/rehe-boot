package com.rehe.storage.s3;

import com.rehe.storage.service.BaseStorageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

/**
 * @description
 * @author rehe
 * @date 2024/8/8
 */
@Configuration
public class S3Config {

    @Bean("adminS3Properties")
    @ConditionalOnProperty(prefix = "storage.s3.admin", name = "endpoint")
    @ConfigurationProperties(prefix = "storage.s3.admin")
    public S3Properties adminS3Properties(){
        return new S3Properties();
    }

    @Bean("adminS3Service")
    @ConditionalOnBean(name = "adminS3Properties")
    @Primary
    public BaseStorageService adminS3Service(@Qualifier("adminS3Properties") S3Properties s3Properties) {
        return new S3Service(s3Client(s3Properties));
    }


    @Bean("appS3Properties")
    @ConditionalOnProperty(prefix = "storage.s3.app", name = "endpoint")
    @ConfigurationProperties(prefix = "storage.s3.app")
    public S3Properties appS3Properties(){
        return new S3Properties();
    }

    @Bean("appS3Service")
    @ConditionalOnBean(name = "appS3Properties")
    public BaseStorageService appS3Service(@Qualifier("appS3Properties") S3Properties s3Properties) {
        return new S3Service(s3Client(s3Properties));
    }

    private S3Client s3Client(S3Properties s3ClientProperties) {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(s3ClientProperties.getAccessKeyId(), s3ClientProperties.getSecretKey());
        return S3Client.builder()
                .endpointOverride(URI.create(s3ClientProperties.getEndpoint()))
                .region(Region.of(s3ClientProperties.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
                .build();
    }
}