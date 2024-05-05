package com.lazzy.mongodb.entity;

import lombok.Data;

@Data
public class UserQueryForm {
    private Integer pageIndex;
    private Integer pageSize;
    private String  username;
}