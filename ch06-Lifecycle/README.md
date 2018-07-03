《深入剖析Tomcat》第六章

# 概要

Lifecycle是一个用于管理生命周期的接口，每个实现该接口的类都会通过addLifecycleListener()保存一系列对该组件感兴趣的监听器。
这些监听器是实现LifecycleListener接口的类。

每个容器及每个组件都会实现Lifecycle接口，方便父容器启动子容器及所有的组件。比如Bootstrap会调用Engine.start()，启动Engine以及所有Engine内的组件（组件的启动是在start()方法内启动的)，同理，当Engine要启动Host时，也是启动Host及其全部的组件。

除了统一启动/关闭所有组件外，Lifecycle也具有传递消息的能力。
