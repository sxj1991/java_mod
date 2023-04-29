package com.example.mybatis.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


@ToString
@Data
public class Note implements Serializable {

    private String noteId;

    private String noteTitle;

    private String noteContent;
}
