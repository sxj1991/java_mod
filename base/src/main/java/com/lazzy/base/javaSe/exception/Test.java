package com.lazzy.base.javaSe.exception;

import java.util.Random;

public class Test {
    public static void main(String[] args) {
        int random = new Random().nextInt(100);
        if(random%2==0){
            // 异常链 根据传入异常信息 帮助用户排查异常问题
            throw new BaseException.BaseExceptionRuntime("触发非受检异常", new IllegalArgumentException("参数错误"));
        }else {
            try {
                throw new BaseException.BaseExceptionBasic("触发受检异常");
            } catch (BaseException.BaseExceptionBasic e) {
                System.out.println("处理异常。。。");
            }
        }
        System.out.println("程序继续运行");

    }
}
