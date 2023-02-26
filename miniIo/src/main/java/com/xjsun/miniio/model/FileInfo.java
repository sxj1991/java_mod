package com.xjsun.miniio.model;

import lombok.Data;

/**
 * 文件信息实体类
 */
@Data
public class FileInfo {
    private String fileName;
    // 布尔值以is开头取名可能存在序列化问题
    private Boolean directory;
}
