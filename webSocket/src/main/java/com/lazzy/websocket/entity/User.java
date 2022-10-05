package com.lazzy.websocket.entity;


import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data

public class User implements Serializable{

    private static final long serialVersionUID = -6503710493488425450L;

    private Integer id;
    private String name;
    private String password;



}