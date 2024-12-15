package com.xjsun.filestore.util;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;

import java.io.*;
import java.util.UUID;

public final class UploadUtil {

    private final static String SEPARATING = "/";

    private final static String FLAG_FILE_NAME = "file.done";


    private final static String MIDDLE_FILE_NAME = "_middle_";

    // 10 KB 缓冲区
    private final static int BUFFER_SIZE = 1024 * 10;

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
        String middleName = filePath + SEPARATING + fileName + MIDDLE_FILE_NAME + UUID.fromString(fileName);
        // 上传文件
        channel.put(new ByteArrayInputStream(fileBytes), middleName);
        // 上传成功修改原始文件名
        channel.rename(middleName, filePath + SEPARATING + fileName);
    }

    /**
     * 设置文件上传速度
     * @param channel
     * @param fileBytes
     * @param filePath
     * @param fileName
     * @param MAX_BYTES_PER_SECOND
     */
    public static void limitSpeed(ChannelSftp channel, byte[] fileBytes, String filePath, String fileName,
                                  long MAX_BYTES_PER_SECOND) {
        UploadMonitor monitor = new UploadMonitor(fileBytes.length);

        try (OutputStream os = channel.put(filePath + SEPARATING + fileName, monitor, ChannelSftp.OVERWRITE);
             InputStream fis = new ByteArrayInputStream(fileBytes)) {

            byte[] buff = new byte[BUFFER_SIZE];
            int read;
            long startTime = System.currentTimeMillis();
            long bytesTransferred = 0;

            while ((read = fis.read(buff)) != -1) {
                os.write(buff, 0, read);
                os.flush();
                bytesTransferred += read;

                // 控制传输速度
                long elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime > 0) {
                    long expectedTime = (bytesTransferred * 1000) / MAX_BYTES_PER_SECOND;
                    if (expectedTime > elapsedTime) {
                        Thread.sleep(expectedTime - elapsedTime);
                    }
                }
            }
            // 确保最后的数据写入完成
            os.flush();
        } catch (SftpException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 上传进度监控器类
     */
    static class UploadMonitor implements SftpProgressMonitor {
        private final long maxBytes;
        private long bytesTransferred;

        public UploadMonitor(long maxBytes) {
            this.maxBytes = maxBytes;
            this.bytesTransferred = 0;
        }

        @Override
        public void init(int op, String src, String dest, long max) {
            System.out.println("开始上传: " + src + " 到" + dest);
        }

        @Override
        public boolean count(long count) {
            bytesTransferred += count;
            System.out.printf("上传字节:\r", bytesTransferred, maxBytes);
            return true;
        }

        @Override
        public void end() {
            System.out.println("\n上传完毕");
        }

    }
}
