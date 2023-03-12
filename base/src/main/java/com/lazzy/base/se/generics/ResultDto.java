package com.lazzy.base.se.generics;

import lombok.Data;


public class ResultDto<T> {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
