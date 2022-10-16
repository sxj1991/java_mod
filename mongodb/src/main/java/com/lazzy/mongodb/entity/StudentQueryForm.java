package com.lazzy.mongodb.entity;

import lombok.Data;

@Data
public class StudentQueryForm {
    private Integer pageIndex;
    private Integer pageSize;
    private String  username;
}