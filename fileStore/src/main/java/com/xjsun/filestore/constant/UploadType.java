package com.xjsun.filestore.constant;

public enum UploadType {
    FLAG_FILE("flag"), INTERMEDIATE_FILE_NAME("middle");

    UploadType(String type) {
        this.type = type;
    }

    private final String type;


    public String getType() {
        return type;
    }
}