package com.xjsun.minIO.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
	
	@Value("${minio.url}")
    private String url;
    @Value("${minio.accessKey}")
    private String accessKey;
    @Value("${minio.secretKey}")
    private String secretKey;

    /**
     * 创建对象存储客户端
     * @return 返回客户端交给spring容器管理
     */
    @Bean
    public MinioClient getMinioClient() {
        return MinioClient.builder().endpoint(url)
				.credentials(accessKey, secretKey).build();
    }
    
}