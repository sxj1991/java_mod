package com.xjsun.filestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * file store 模块在于测试jsch文件传输功能
 * 重点实现文件传输时，如何保证文件的完整性和安全性
 * 1. 实现中间文件方式，保证文件完整性
 * 2. 实现标志文件方式，保证文件完整性
 * 3. 实现根据文件配置保证文件顺序传输
 */
@SpringBootApplication
public class FileStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileStoreApplication.class, args);
    }

}
