package com.xjsun.filestore.controller;

import com.xjsun.filestore.constant.UploadType;
import com.xjsun.filestore.controller.vo.Response;
import com.xjsun.filestore.service.FileUploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

    private final FileUploadService uploadService;

    public FileUploadController(FileUploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("up")
    public Response<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("type") String uploadType) {
        try {
            uploadService.fileUpload(file, UploadType.valueOf(uploadType));
        } catch (Exception e) {
            return Response.fail();
        }
        return Response.success();
    }
}
