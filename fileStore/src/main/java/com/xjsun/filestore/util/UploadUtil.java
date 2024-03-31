package com.xjsun.filestore.util;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public final class UploadUtil {

    private final static String SEPARATING = "/";

    private final static String FLAG_FILE_NAME = "file.done";


    private final static String MIDDLE_FILE_NAME = "_middle_";

    private UploadUtil() {
        throw new RuntimeException("工具类不需要直接创建");
    }

    /**
     * 通过上传成功的标志文件
     * 保证文件的完整性
     */
    public static void flagUploadFile(ChannelSftp channel, byte[] fileBytes, String filePath, String fileName)
            throws SftpException {
        // 上传文件
        channel.put(new ByteArrayInputStream(fileBytes), filePath + SEPARATING + fileName);
        // 上传成功上传标志文件
        try (InputStream fileStream = new ByteArrayInputStream("".getBytes())) {
            channel.put(fileStream, filePath + SEPARATING + FLAG_FILE_NAME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过修改文件名称，使用中间文件名，上传完毕再次修改为原文件名
     * 保证文件的完整性
     */
    public static void middleUploadFile(ChannelSftp channel, byte[] fileBytes, String filePath, String fileName)
            throws SftpException {
        // 上传文件
        channel.put(new ByteArrayInputStream(fileBytes),
                filePath + SEPARATING + fileName + MIDDLE_FILE_NAME + UUID.fromString(fileName));
        // 上传成功修改原始文件名
        channel.rename(filePath + SEPARATING + fileName + MIDDLE_FILE_NAME + UUID.fromString(fileName),
                filePath + SEPARATING + fileName);
    }


}
