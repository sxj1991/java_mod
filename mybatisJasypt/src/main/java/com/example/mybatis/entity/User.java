package com.example.mybatis.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


@ToString
@Data
public class User implements Serializable {

    private String userId;

    private String userName;

    private String password;

    private Integer status;
}
