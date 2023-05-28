package com.example.mybatis.tool;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 加密配置工具EncryptionConfig
 *
 * @author zhangyh
 * @date 2022-12-12
 */
@Configuration
@EnableEncryptableProperties
public class EncryptionConfig {
    //加密算法
    private static final String ALGORITHM = "PBEWithHMACSHA512AndAES_256";

    //加密密钥
    private static final String PASSWORD = "qazwsx";

    @Bean("jasyptStringEncryptor")
    public StringEncryptor jasyptStringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(PASSWORD);
        config.setAlgorithm(ALGORITHM);
        config.setPoolSize("1");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }

}