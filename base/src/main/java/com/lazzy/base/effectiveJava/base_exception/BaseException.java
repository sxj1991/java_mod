package com.lazzy.base.effectiveJava.base_exception;

/**
 * 异常使用说明：
 * 1. 异常类型：非受检异常、受检异常
 * 2. 异常链传递
 */
public class BaseException {
    /**
     * 非受检异常：用户无需处理，程序异常会报错
     */
    public static class BaseExceptionRuntime extends RuntimeException{
        public BaseExceptionRuntime(String message, Throwable e) {
            super(message, e);
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

    /**
     * 异常链以及异常转译
     * 异常转译：通过将比较底层异常转为高层异常，让异常链路更加清晰，报错信息更加具体
     */
    public static class BaseExceptionChain{
        public void chain() throws BaseExceptionBasic {
            JUCException jucException = new JUCException();
            try {
                jucException.sleep();
            } catch (InterruptedException e) {
                // 将InterruptedException异常 转为BaseExceptionBasic异常
                // 向上抛出，由更上一层调用用户进行处理
                throw new BaseExceptionBasic(e.getMessage());
            }

        }
    }

    /**
     * 用户异常链路测试
     */
    public static class JUCException{
        public void sleep() throws InterruptedException {
            Thread.sleep(1000);
        }
    }

}
