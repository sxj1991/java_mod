package com.lazzy.base.designPatterns.mediator;

import java.util.ArrayList;
import java.util.List;

/**
 * 中介者模式（Mediator Pattern）是一种行为设计模式，它通过引入一个中介对象来简化多个对象之间的复杂交互。
 * 这种模式的目的是减少直接通信之间的类的依赖性，防止组件之间的紧密耦合，使其更容易维护。
 * <p>
 * 组件
 * 中介者模式主要包括以下几个组件：
 * <p>
 * 中介者接口（Mediator）：定义了通信的一个接口，用于各对象（Colleague）之间的通信。
 * <p>
 * 具体中介者（Concrete Mediator）：实现中介者接口并协调各个类（Colleague）之间的交互。
 * <p>
 * 同事类（Colleague）：各个类不直接通信，而是通过中介者来进行通信。
 */
public class MsgMediator {
    // 中介者接口
    interface ChatMediator {
        void sendMessage(String msg, User user);
        void addUser(User user);
    }

    // 具体中介者
    private static class ChatRoom implements ChatMediator {
        private List<User> users;

        public ChatRoom() {
            this.users = new ArrayList<>();
        }

        @Override
        public void addUser(User user) {
            this.users.add(user);
        }

        @Override
        public void sendMessage(String msg, User user) {
            for (User u : users) {
                // 消息不应该被发送者接收
                if (u != user) {
                    u.receive(msg);
                }
            }
        }
    }

    // 用户类
    private static abstract class User {
        protected ChatMediator mediator;
        protected String name;

        public User(ChatMediator med, String name) {
            this.mediator = med;
            this.name = name;
        }

        public abstract void send(String msg);
        public abstract void receive(String msg);
    }

    // 具体聊天用户类
    private static class ChatUser extends User {

        public ChatUser(ChatMediator med, String name) {
            super(med, name);
        }

        @Override
        public void send(String msg) {
            System.out.println(this.name + ": Sending Message=" + msg);
            mediator.sendMessage(msg, this);
        }

        @Override
        public void receive(String msg) {
            System.out.println(this.name + ": Received Message:" + msg);
        }
    }

    public static void main(String[] args) {
        ChatMediator mediator = new ChatRoom();

        User user1 = new ChatUser(mediator, "zhangsan");
        User user2 = new ChatUser(mediator, "lisi");
        User user3 = new ChatUser(mediator, "wangwu");

        mediator.addUser(user1);
        mediator.addUser(user2);
        mediator.addUser(user3);

        user1.send("hello");
    }
}
