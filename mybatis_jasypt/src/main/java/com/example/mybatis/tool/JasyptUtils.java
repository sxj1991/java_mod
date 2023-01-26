package com.example.mybatis.tool;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.properties.PropertyValueEncryptionUtils;
import org.jasypt.util.text.AES256TextEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.jasypt.util.text.TextEncryptor;

@Slf4j
public final class JasyptUtils {
    /**
     * 加密使用密钥
     */
    private static final String PRIVATE_KEY = "qazwsx";

    private static AES256TextEncryptor textEncryptor = new AES256TextEncryptor();

    static {
        textEncryptor.setPassword(PRIVATE_KEY);
    }

    /**
     * 私有构造方法，防止被意外实例化
     */
    private JasyptUtils() {
    }

    /**
     * 明文加密
     *
     * @param plaintext 明文
     * @return String
     */
    public static String encrypt(String plaintext) {
        log.info("明文字符串为：{}", plaintext);
        // 使用的加密算法参考2.2节内容，也可以在源码的类注释中看到
        String ciphertext = textEncryptor.encrypt(plaintext);
        log.info("密文字符串为：{}", ciphertext);
        return ciphertext;
    }

    /**
     * 解密
     *
     * @param ciphertext 密文
     * @return String
     */
    public static String decrypt(String ciphertext) {
        log.info("密文字符串为：{}", ciphertext);
        ciphertext = "ENC(" + ciphertext + ")";
        if (PropertyValueEncryptionUtils.isEncryptedValue(ciphertext)) {
            String plaintext = PropertyValueEncryptionUtils.decrypt(ciphertext, textEncryptor);
            log.info("明文字符串为：{}", plaintext);
            return plaintext;
        }
        log.error("解密失败！");
        return "";
    }

    public static void main(String[] args) {
       encrypt("12345678");
       decrypt("zfR/yUxcptzW1ZGJQMB1o5KYQqcGQPail09cPaMFgZm1ZuZiFhOPatBS3UrnHJ/b");
    }
}