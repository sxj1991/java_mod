package com.xjsun.miniio.controller;


import com.xjsun.miniio.model.FileInfo;
import com.xjsun.miniio.model.Response;
import com.xjsun.miniio.service.impl.MinioService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping(value = "/mini")
public class FileController {

    private final MinioService minioService;

    public FileController(MinioService minioService) {
        this.minioService = minioService;
    }


    @PostMapping(value = "/upload")
    public Response<Object> fileUpload(@RequestParam MultipartFile upload, @RequestParam String bucket,
                                       @RequestParam(required = false) String objectName) throws Exception {
        minioService.createBucket(bucket);
        if (objectName != null) {
            minioService.uploadFile(upload.getInputStream(), bucket, objectName + "/" + upload.getOriginalFilename());
        } else {
            minioService.uploadFile(upload.getInputStream(), bucket, upload.getOriginalFilename());
        }
        return Response.success();
    }


    @GetMapping(value = "/buckets")
    public Response<List<String>> listBuckets() throws Exception {
        return Response.success(minioService.listBuckets());
    }


    @GetMapping(value = "/files")
    public Response<List<FileInfo>> listFiles(@RequestParam String bucket) throws Exception {
        return Response.success(minioService.listFiles(bucket));
    }


    @GetMapping(value = "/download")
    public void downloadFile(@RequestParam String bucket, @RequestParam String objectName,
                             HttpServletResponse response) throws Exception {
        InputStream stream = getRemoteMiniIOStream(bucket, objectName);
        ServletOutputStream output = response.getOutputStream();
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode
                (objectName.substring(objectName.lastIndexOf("/") + 1), StandardCharsets.UTF_8));
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        IOUtils.copy(stream, output);
    }

    @GetMapping(value = "/look")
    public Response<String> lookFile(@RequestParam String objectName, @RequestParam String bucket
    ) {

        return Response.success(minioService.getPreviewUrl(objectName, bucket));
    }

    private InputStream getRemoteMiniIOStream(String bucket, String objectName) throws Exception {
        return minioService.download(bucket, objectName);
    }


    @GetMapping(value = "/deleteFile")
    public Response<Object> deleteFile(@RequestParam String bucket, @RequestParam String objectName) throws Exception {
        minioService.deleteObject(bucket, objectName);
        return Response.success();
    }


    @GetMapping(value = "/deleteBucket")
    public Response<Object> deleteBucket(@RequestParam String bucket) throws Exception {
        minioService.deleteBucket(bucket);
        return Response.success();
    }


    @GetMapping("/copyObject")
    public Response<Object> copyObject(@RequestParam String sourceBucket, @RequestParam String sourceObject, @RequestParam String targetBucket, @RequestParam String targetObject) throws Exception {
        minioService.copyObject(sourceBucket, sourceObject, targetBucket, targetObject);
        return Response.success();
    }

    @GetMapping("/getObjectInfo")
    public Response<String> getObjectInfo(@RequestParam String bucket, @RequestParam String objectName) throws Exception {

        return Response.success(minioService.getObjectInfo(bucket, objectName));
    }

    @GetMapping("/url")
    public Response<String> getResignedObjectUrl(@RequestParam String bucket, @RequestParam String objectName) throws Exception {

        return Response.success(minioService.getResignedObjectUrl(bucket, objectName));
    }

    @GetMapping("/all")
    public Response<List<FileInfo>> listAllFile() throws Exception {

        return Response.success(minioService.listAllFile());
    }


}