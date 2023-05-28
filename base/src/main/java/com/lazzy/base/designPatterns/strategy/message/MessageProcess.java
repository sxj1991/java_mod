package com.lazzy.base.designPatterns.strategy.message;

/**
 * 消息类型枚举类
 */
public enum MessageProcess {
    Link("链接"),
    Text("文本信息"),
    File("文件信息");
    private String messageType;

    MessageProcess(String messageType) {
        this.messageType = messageType;
    }
}
