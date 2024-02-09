## JAVA 基础相关知识整理

### 一、Java 设计模式编码实现

##### 1. 桥接模式

> 桥接模式（Bridge Pattern）是一种结构型设计模式，它的主要目的是将抽象部分与其实现部分分离，使它们都可以独立地变化。
>
> 这种模式通过提供桥接结构，实现了抽象和实现的解耦。在桥接模式中，可以改变抽象和实现部分的代码，而不会互相影响。

:smile:详情点击：[桥接模式](/base/src/main/java/com/lazzy/base/designPatterns/bridge/DocumentBridge.java)

##### 2.适配器模式

> 将一个类的接口转换成客户希望的另外一个接口，适配器模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作。
>
> 同时需要注意的是该模式，不是首选解决问题模式，而是无法改动旧有接口时，创建新接口兼容旧接口。

:rocket:详情点击： [适配器模式](/base/src/main/java/com/lazzy/base/designPatterns/adapter/MailSend.java)

##### 3. 迭代器模式

> 将容器的添加、修改与遍历方式解耦，优势在于可以根据不同容器设计不同遍历算法。

:bowling:详情点击： [迭代器模式](/base/src/main/java/com/lazzy/base/designPatterns/iterator/Iterator.java)


##### 4.组合模式

> 组合模式（**Composite Pattern**）是一种结构型设计模式，它允许将对象组合成树形结构以表示部分**-**整体的层次结构。
>
> 常见的使用方式，设计角色菜单类型，组织结构等等。

:panda_face: 详情点击：[组合模式](src%2Fmain%2Fjava%2Fcom%2Flazzy%2Fbase%2FdesignPatterns%2Fcomposite%2FOrgComposite.java)

##### 5.装饰器模式

> 装饰器模式（Decorator Pattern）允许向一个现有的对象添加新的功能，同时又不改变其结构。这种类型的设计模式属于结构型模式，它是作为现有的类的一个包装。
>
> 装饰器模式通过将对象包装在装饰器类中，以便动态地修改其行为。
>
> 这种模式创建了一个装饰类，用来包装原有的类，并在保持类方法签名完整性的前提下，提供了额外的功能。

:honeybee: 详情点击：[装饰器模式](src%2Fmain%2Fjava%2Fcom%2Flazzy%2Fbase%2FdesignPatterns%2Fdecorator%2FTypeDecorator.java)

##### 6.工厂模式

> 工厂模式（Factory Pattern）是 Java 中最常用的设计模式之一，这种类型的设计模式属于创建型模式，它提供了一种创建对象的最佳方式。
>
> 工厂模式提供了一种创建对象的方式，而无需指定要创建的具体类。
>
> 工厂模式属于创建型模式，它在创建对象时提供了一种封装机制，将实际创建对象的代码与使用代码分离。
>
> 工厂方法比起一般构造方法创建对象具有更佳的可读性。

:blue_heart: 详情点击：[工厂模式](src%2Fmain%2Fjava%2Fcom%2Flazzy%2Fbase%2FdesignPatterns%2Ffactory%2Fsimple%2FEventFactory.java)

##### 7.代理模式

> 在代理模式（Proxy Pattern）中，一个类代表另一个类的功能。这种类型的设计模式属于结构型模式。
>
> 在代理模式中，我们创建具有现有对象的对象，以便向外界提供功能接口。
>
> 常见的使用方式就是切面，就是使用代理类完成方法调用，非侵入方式增强该方法。

:cherries: 详情点击：[代理模式](src%2Fmain%2Fjava%2Fcom%2Flazzy%2Fbase%2FdesignPatterns%2Fproxy%2FTimeAdvice.java)

##### 8. 观察者模式

> 有一个另外的叫法订阅发布模式，对信息发布和数据多方接收进行解耦。
>
> 观察者模式是一种行为型设计模式，它定义了一种一对多的依赖关系，当一个对象的状态发生改变时，其所有依赖者都会收到通知并自动更新。
>
> 当对象间存在一对多关系时，则使用观察者模式（Observer Pattern）。比如，当一个对象被修改时，则会自动通知依赖它的对象。观察者模式属于行为型模式。
>
> 当对象状态改变给其他对象通知的问题，而且要考虑到易用和低耦合，保证高度的协作。

:crescent_moon: 详情点击：[观察者模式](src%2Fmain%2Fjava%2Fcom%2Flazzy%2Fbase%2FdesignPatterns%2Fobserver%2Flistener%2FUploadImageListener.java)

##### 9.策略模式

> 在策略模式定义了一系列算法或策略，并将每个算法封装在独立的类中，java面向对象多态特性，使得它们可以互相替换。通过使用策略模式，可以在运行时根据需要选择不同的算法，而不需要修改客户端代码。

:seat: 详情点击：[策略模式](src%2Fmain%2Fjava%2Fcom%2Flazzy%2Fbase%2FdesignPatterns%2Fstrategy%2Ffactory%2FMessageServiceStrategyFactory.java)

##### 10.模板方法

> 模板方法模式定义一个操作中算法的框架，而将一些步骤延迟到子类中。模板方法模式使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤。
>
> 模板方法模式是一种基于继承的代码复用技术，它是一种类行为型模式。
>
> 模板方法模式是结构最简单的行为型设计模式，在其结构中只存在父类与子类之间的继承关系。通过使用模板方法模式，可以将一些复杂流程的实现步骤封装在一系列`基本方法`中，在抽象父类中提供一个称之为`模板方法`的方法来定义这些基本方法的执行次序，而通过其子类来覆盖某些步骤，从而使得相同的算法框架可以有不同的执行结果。

:dolphin: 详情点击：[模板方法](src%2Fmain%2Fjava%2Fcom%2Flazzy%2Fbase%2FdesignPatterns%2FtemplateMethod%2FAbstractConnectServer.java)

##### 11.责任链模式

> 有时候为了消除一系列if...else语句判断，责任链模式是一个不错的选择。
>
> 通过维护一个链表数据结构，每个处理类根据链表依次传递，处理逻辑和处理顺序的解耦，spring框架常见过滤器和拦截器就属于此模式。

:deciduous_tree: 详情点击：[责任链模式](src%2Fmain%2Fjava%2Fcom%2Flazzy%2Fbase%2FdesignPatterns%2FresponsibilityChain%2FHandler.java)

##### 12.中介者模式

> 中介者模式（**Mediator Pattern**）是一种行为设计模式，它通过引入一个中介对象来简化多个对象之间的复杂交互。
> 这种模式的目的是减少直接通信之间的类的依赖性，防止组件之间的紧密耦合，使其更容易维护。

:tanabata_tree: 详情点击：[中介者模式](src%2Fmain%2Fjava%2Fcom%2Flazzy%2Fbase%2FdesignPatterns%2Fmediator%2FMsgMediator.java)

##### 13. 备忘录模式

> 备忘录模式（**Memento Pattern**）是一种行为设计模式，它允许捕获和存储一个对象的当前状态，以便在未来的某个时刻可以恢复到这个状态。
> 备忘录模式通过三个角色实现：原发器（**Originator**）、备忘录（**Memento**）和负责人（**Caretaker**）。

:palm_tree: 详情点击：[备忘录模式](src%2Fmain%2Fjava%2Fcom%2Flazzy%2Fbase%2FdesignPatterns%2Fmemento%2FMsgMemento.java)

##### 14.单例模式

> 程序中有时候只需要维护一个对象即可，比如配置信息对象，工具类对象。
>
> 单例模式（Singleton Pattern）是 Java 中最简单的设计模式之一。这种类型的设计模式属于创建型模式，它提供了一种创建对象的最佳方式。

:cloud: 详情点击：[单例模式](src%2Fmain%2Fjava%2Fcom%2Flazzy%2Fbase%2FdesignPatterns%2Fsingleton%2FSingletonBase.java)

##### 15. 状态机模式

> 在状态模式（State Pattern）中，类的行为是基于它的状态改变的。这种类型的设计模式属于行为型模式。
>
> 在状态模式中，我们创建表示各种状态的对象和一个行为随着状态对象改变而改变的 context 对象。
>
> **意图：**允许对象在内部状态发生改变时改变它的行为，对象看起来好像修改了它的类。
>
> **主要解决：**对象的行为依赖于它的状态（属性），并且可以根据它的状态改变而改变它的相关行为。
>
> 工作流程、事件审批等需求都可以通过该问题解决，也有相当多第三方库可以集成。

:alarm_clock: 详情点击：[状态机模式](src%2Fmain%2Fjava%2Fcom%2Flazzy%2Fbase%2FdesignPatterns%2Fstate%2FStateMachineConfig.java)