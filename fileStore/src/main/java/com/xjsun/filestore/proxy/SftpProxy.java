package com.xjsun.filestore.proxy;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;


/**
 * @author mrs.sun
 */
public class SftpProxy {

    // SFTP服务器信息
    private static final String SFTP_HOST = "127.0.0.1";
    private static final int SFTP_PORT = 2022;

    public static void main(String[] args) {
        // 代理服务器监听的端口号
        int proxyPort = 5001;

        try (ServerSocket serverSocket = new ServerSocket(proxyPort)) {
            System.out.println("Proxy server started on port " + proxyPort);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());



                // 简单的IP检查示例，实际应用中可能需要更复杂的逻辑
                if (!isValidClientIp(clientSocket.getInetAddress().getHostAddress())) {
                    System.err.println("Unauthorized client IP: " + clientSocket.getInetAddress());
                    clientSocket.close();
                    continue;
                }

                //获取clientSocket 用户名称
                System.out.println("SFTP server started on port " + SFTP_PORT);

                // 启动处理客户端连接的线程
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (
                    InputStream clientInput = clientSocket.getInputStream();
                    OutputStream clientOutput = clientSocket.getOutputStream();
                    Socket sftpSocket = new Socket(SFTP_HOST, SFTP_PORT);
                    InputStream sftpInput = sftpSocket.getInputStream();
                    OutputStream sftpOutput = sftpSocket.getOutputStream()
            ) {

                // 这里简化处理，直接转发数据，实际应用中应根据协议解析并适当处理命令
                Thread clientToSftp = new Thread(() -> {
                    try {
                        forwardData(clientInput, sftpOutput);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                Thread sftpToClient = new Thread(() -> {
                    try {
                        forwardData(sftpInput, clientOutput);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                clientToSftp.start();
                sftpToClient.start();

                clientToSftp.join();
                sftpToClient.join();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        private void forwardData(InputStream in, OutputStream out) throws IOException {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                out.flush();
            }
        }
    }

    // 示例：检查客户端IP是否允许连接，这里仅作为示例，实际应用需根据需求调整
    private static boolean isValidClientIp(String ipAddress) {
        // 假设只允许本地连接作为示例
        return "127.0.0.1".equals(ipAddress);
    }
}