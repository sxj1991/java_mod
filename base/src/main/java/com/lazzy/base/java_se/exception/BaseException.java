package com.lazzy.base.java_se.exception;

/**
 * 受检异常 非受检异常基础演示
 * 继承的类不同
 */
public class BaseException {

    /**
     * 非受检异常：用户无需处理，程序异常会报错
     */
    public static class BaseExceptionRuntime extends RuntimeException{
        public BaseExceptionRuntime(String message) {
            super(message);
        }
    }
    /**
     * 受检异常：用户需手动处理该异常 否则编译器报错
     */
    public static class BaseExceptionBasic extends Exception{
        public BaseExceptionBasic(String message) {
            super(message);
        }
    }
}
