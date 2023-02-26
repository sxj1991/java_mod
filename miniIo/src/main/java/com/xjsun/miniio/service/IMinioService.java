package com.xjsun.miniio.service;

import com.xjsun.miniio.model.FileInfo;

import java.io.InputStream;
import java.util.List;

public interface IMinioService {
    /**
     * 获取minio中所有的文件
     */
    List<FileInfo> listAllFile() throws Exception;

    /**
     * 生成一个给HTTP GET请求用的presigned URL。
     * 浏览器/移动端的客户端可以用这个URL进行下载，即使其所在的存储桶是私有的。
     */
    String getResignedObjectUrl(String bucketName, String objectName) throws Exception;

    /**
     * 获取文件信息
     */
    String getObjectInfo(String bucket, String objectName) throws Exception;

    /**
     * 复制文件
     */
    void copyObject(String sourceBucket, String sourceObject, String targetBucket, String targetObject)
            throws Exception;

    /**
     * 删除一个对象
     */
    void deleteObject(String bucket, String objectName) throws Exception;

    /**
     * 删除一个桶
     */
    void deleteBucket(String bucket) throws Exception;

    /**
     * 下载一个文件
     */
    InputStream download(String bucket, String objectName) throws Exception;

    /**
     * 列出一个桶中的所有文件和目录
     */
    List<FileInfo> listFiles(String bucket) throws Exception;

    /**
     * 列出所有的桶
     */
    List<String> listBuckets() throws Exception;

    /**
     * 上传一个文件
     */
    void uploadFile(InputStream stream, String bucket, String objectName) throws Exception;

    /**
     * 创建一个桶
     */
    void createBucket(String bucket) throws Exception;

    /**
     * 获取可预览的图片地址
     */
    String getPreviewUrl(String object, String bucketName);

}
