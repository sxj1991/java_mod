package com.xjsun.minIO.service.impl;

import com.xjsun.minIO.model.FileInfo;
import com.xjsun.minIO.service.IMinioService;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class MinioService implements IMinioService {
    private final MinioClient minioClient;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public void createBucket(String bucket) throws Exception {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }
    }

    public void uploadFile(InputStream stream, String bucket, String objectName) throws Exception {
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs.builder().bucket(bucket).object(objectName)
                .stream(stream, -1, 10485760).build());
        System.out.println(objectWriteResponse.etag());
        System.out.println(objectWriteResponse.bucket());
        System.out.println(objectWriteResponse.object());
    }

    public List<String> listBuckets() throws Exception {
        List<Bucket> list = minioClient.listBuckets();
        List<String> names = new ArrayList<>();
        list.forEach(b -> {
            names.add(b.name());
        });
        return names;
    }


    public List<FileInfo> listFiles(String bucket) throws Exception {
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(bucket).recursive(true).build());

        List<FileInfo> infos = new ArrayList<>();
        results.forEach(r -> {
            FileInfo info = new FileInfo();
            try {
                Item item = r.get();
                info.setFileName(item.objectName());
                info.setDirectory(item.isDir());
                infos.add(info);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return infos;
    }

    public InputStream download(String bucket, String objectName) throws Exception {
        InputStream stream = minioClient.getObject(
                GetObjectArgs.builder().bucket(bucket).object(objectName).build());
        return stream;
    }

    public void deleteBucket(String bucket) throws Exception {
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucket).build());
    }


    public void deleteObject(String bucket, String objectName) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(objectName).build());
    }


    public void copyObject(String sourceBucket, String sourceObject, String targetBucket, String targetObject)
            throws Exception {
        this.createBucket(targetBucket);
        minioClient.copyObject(CopyObjectArgs.builder().bucket(targetBucket).object(targetObject)
                .source(CopySource.builder().bucket(sourceBucket).object(sourceObject).build()).build());
    }


    public String getObjectInfo(String bucket, String objectName) throws Exception {

        return minioClient.statObject(StatObjectArgs.builder().bucket(bucket).object(objectName).build()).toString();

    }

    public String getResignedObjectUrl(String bucketName, String objectName) throws Exception {
        GetPresignedObjectUrlArgs build = GetPresignedObjectUrlArgs
                .builder().bucket(bucketName).object(objectName).method(Method.GET).build();
        return minioClient.getPresignedObjectUrl(build);
    }


    public List<FileInfo> listAllFile() throws Exception {
        List<String> list = this.listBuckets();
        List<FileInfo> info = new ArrayList<>();
        for (String bucketName : list) {
            info.addAll(this.listFiles(bucketName));
        }


        return info;
    }

    public String getPreviewUrl(String object, String bucketName) {
        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("response-content-type", "image/jpg");
        String url = null;
        try {
            url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(object)
                            .expiry(7, TimeUnit.DAYS)
                            .extraQueryParams(reqParams)
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }


}