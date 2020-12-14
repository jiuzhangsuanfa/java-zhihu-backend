package com.jiuzhang.zhihu.config;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

//import org.checkerframework.checker.units.qual.A;


@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

    // endPoint是一个URL，域名，IPv4或者IPv6地址
    @Value("{minio.endpoint}")
    private String endpoint;

    // TCP/IP端口号
    private int port = 9000;

    // accessKey类似于用户ID，用于唯一标识你的账户
    @Value("{minio.accessKey}")
    private String accessKey;

    // secretKey是你账户的密码
    @Value("{minio.secretKey}")
    private String secretKey;

    // 如果是true，则用的是https而不是http,默认值是true
    private Boolean secure;

    // 默认存储桶
    private String bucketName;

    // 配置目录
    private String configDir;

    @Bean
    public MinioClient getMinioClient() throws InvalidEndpointException, InvalidPortException {
        MinioClient minioClient = new MinioClient(endpoint, port, accessKey, secretKey);
        return minioClient;
    }
}