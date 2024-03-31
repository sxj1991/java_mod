package com.xjsun.filestore.service;


import com.jcraft.jsch.*;
import com.xjsun.filestore.constant.UploadType;
import com.xjsun.filestore.util.UploadUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class FileUploadService {

    @Value("${remote.username}")
    private String username;

    @Value("${remote.password}")
    private String password;

    @Value("${remote.host}")
    private String host;

    @Value("${remote.port}")
    private Integer port;

    @Value("${remote.file-path}")
    private String filePath;

    public void fileUpload(MultipartFile file, UploadType type) throws Exception {
        if (ObjectUtils.isEmpty(file)) {
            return;
        }
        Session session = null;
        ChannelSftp channel = null;
        try {
            // 打开session
            session = openSession();
            // 打开通道
            channel = openChannel(session);

            // 获取文件内容并上传到远程服务器
            byte[] bytes = file.getBytes();
            // 选择哪种方式保证完整性
            switchFileUpload(file, type, channel, bytes, filePath);
        } catch (JSchException | IOException | SftpException e) {
            throw new Exception("文件上传失败:" + e);
        } finally {
            if (objectNotEmpty(channel)) {
                channel.disconnect();
            }
            if (objectNotEmpty(session)) {
                session.disconnect();
            }
        }
    }

    private Session openSession() throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, port);
        session.setPassword(password);

        // 避免询问密钥确认
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.connect();

        return session;
    }

    private ChannelSftp openChannel(Session session) throws JSchException {
        String connectType = "sftp";
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel(connectType);
        channelSftp.connect();
        return channelSftp;
    }

    private void switchFileUpload(MultipartFile file, UploadType type, ChannelSftp channel, byte[] bytes, String filePath)
            throws SftpException {
        switch (type) {
            case FLAG_FILE -> UploadUtil.flagUploadFile(channel, bytes, filePath, file.getOriginalFilename());
            case INTERMEDIATE_FILE_NAME ->
                    UploadUtil.middleUploadFile(channel, bytes, filePath, file.getOriginalFilename());
            default -> throw new RuntimeException("不存在的上传方式");
        }
    }

    private <T> boolean objectNotEmpty(T t) {
        return !ObjectUtils.isEmpty(t);
    }
}
