package com.lazzy.base.designPatterns.memento;

import lombok.Getter;

/**
 * 备忘录模式（Memento Pattern）是一种行为设计模式，它允许捕获和存储一个对象的当前状态，以便在未来的某个时刻可以恢复到这个状态。
 * 备忘录模式通过三个角色实现：原发器（Originator）、备忘录（Memento）和负责人（Caretaker）。
 * <p>
 * 组件
 * 原发器（Originator）：创建一个包含其当前内部状态的备忘录对象，并可以使用备忘录对象恢复其内部状态。
 * <p>
 * 备忘录（Memento）：存储原发器的内部状态，并可防止原发器以外的对象访问备忘录。
 * <p>
 * 负责人（Caretaker）：负责保存备忘录，但不对备忘录的内容进行操作或检查。
 */
public class MsgMemento {
    private String msg;

    public MsgMemento(String msg) {
        this.msg = msg;
    }

    /**
     * 通过一个备忘录类 来备份还原 MsgOriginator类信息
     */
    private static class MsgOriginator{
        private String msg;

        public MsgOriginator(String msg) {
            this.msg = msg;
        }

        public void saveMsgOriginator(MsgOriginator originator){
            // 负责人角色保存MsgOriginator类对象信息
            MsgCaretaker.saveMemento(new MsgMemento(originator.msg));
        }

        public void restoreFromMemento(MsgOriginator restore) {
            // 负责人角色还原MsgOriginator类对象信息
            restore.msg =  MsgCaretaker.getMemento().msg;
        }
    }

    private static class MsgCaretaker{
        @Getter
        private static MsgMemento memento;

        public static void saveMemento(MsgMemento m) {
            memento = m;
        }

    }

    public static void main(String[] args) {
        MsgOriginator originator = new MsgOriginator("hello");
        // 保存originator对象信息
        originator.saveMsgOriginator(originator);

        // 改变originator对象信息
        originator.msg = "world";

        //还原信息
        originator.restoreFromMemento(originator);

        // 输出 hello
        System.out.println(originator.msg);


    }
}
