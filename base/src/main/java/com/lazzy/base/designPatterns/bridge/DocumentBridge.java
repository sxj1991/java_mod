package com.lazzy.base.designPatterns.bridge;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 桥接模式（Bridge Pattern）是一种结构型设计模式，它的主要目的是将抽象部分与其实现部分分离，使它们都可以独立地变化。
 * 这种模式通过提供桥接结构，实现了抽象和实现的解耦。在桥接模式中，可以改变抽象和实现部分的代码，而不会互相影响。
 * <p>
 * 组件
 * 桥接模式主要包括以下几个组件：
 * <p>
 * 抽象化（Abstraction）：它的实现部分是通过一个引用关联的实现化对象。它通常包含一种类型的功能，这种功能需要委托给实现部分的对象。
 * <p>
 * 细化抽象化（Refined Abstraction）：是抽象化的子类。它实现了在抽象化中定义的抽象方法，通过这些方法可以使用实现部分的具体方法。
 * <p>
 * 实现化（Implementor）：定义实现化角色的接口，这个接口不一定要与抽象化角色的接口完全一致，实际上这两个接口可以完全不同。
 * 通常在实现化角色中仅提供基本操作，而抽象化角色则定义了基于这些基本操作的较高层次的操作。
 * <p>
 * 具体实现化（Concrete Implementor）：实现实现化角色接口并定义它的具体实现
 */
public class DocumentBridge {

    /**
     * 实现化角色接口
     */
    private interface PrintOperation {
        void msg(String text);
    }

    /**具体实现化角色*/
    private static class NormalPrinter implements PrintOperation {
        // 普通打印方式实现
        public void msg(String text) {
            System.out.println("Normal: " + text);
        }
    }

    private static class EncryptedPrinter implements PrintOperation {
        // base64 编码方式打印实现
        public void msg(String text) {
            // 简化的加密显示
            String base64 = Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
            System.out.println("Encrypted" + base64);

        }
    }

    /**
     *  文档桥接的抽象类
     *  根据需要桥接不同打印方式
     */
    private static abstract class Document {
        protected PrintOperation printer;

        public Document(PrintOperation printer) {
            this.printer = printer;
        }

        abstract void print();
    }

    /** 细化抽象化角色 */
    private static class Report extends Document {
        /**
         * 输出内容
         */
        private String content;

        /**
         * 构造器传入桥接实现对象
         */
        public Report(PrintOperation printer, String content) {
            super(printer);
            this.content = content;
        }

        /**
         * 调用父类PrintOperation 实例对象打印
         */
        public void print() {
            printer.msg(content);
        }
    }

    public static void main(String[] args) {
        // 通过方式实际需要，桥接对象不同，结果则不相同，适配更多场景
        PrintOperation encryptedPrinter = new EncryptedPrinter();
        Document documentEncrypt = new Report(encryptedPrinter, "hello");
        documentEncrypt.print();

        PrintOperation normalPrinter = new NormalPrinter();
        Document documentNormal= new Report(normalPrinter, "hello");
        documentNormal.print();
    }

}
