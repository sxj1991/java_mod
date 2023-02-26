package com.xjsun.miniio.model;

import lombok.Data;

@Data
public class Response<T> {
    private Integer code;
    private T data;
    private String msg;

    public static <T> Response<T> success() {
        return build(200, "success");
    }

    public static <T> Response<T> success(T data) {
        return build(200, data, "success");
    }

    public static <T> Response<T> fail() {
        return build(4004, "fail");
    }


    private static <T> Response<T> build(Integer code, T data, String msg) {
        Response<T> resp = new Response<>();
        resp.setCode(code);
        resp.setData(data);
        resp.setMsg(msg);
        return resp;
    }

    private static <T> Response<T> build(Integer code, String msg) {
        Response<T> resp = new Response<>();
        resp.setCode(code);
        resp.setMsg(msg);
        return resp;
    }


}
