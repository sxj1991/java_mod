package com.lazzy.doublesourcejpa.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_user")
@ToString
@Data
public class User implements Serializable {

    @Id
    @Column(columnDefinition = "user_id")
    private String userId;

    @Column(columnDefinition = "user_name")
    private String userName;

    @Column(columnDefinition = "password")
    private String password;

    @Column(columnDefinition = "status")
    private Integer status;
}
