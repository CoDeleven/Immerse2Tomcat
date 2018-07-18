《深入剖析Tomcat》第十二章

# 概要
前面几章的内容，都是main()方法结尾时主动调用close()才会关闭Context。
我们在实际使用Tomcat的时候肯定不是这样的，所以本章就是了解Tomcat如何启动、关闭容器的

![服务器组件和服务组件](https://blog-1252749790.file.myqcloud.com/tomcat/Tomcat%E6%9C%8D%E5%8A%A1%E5%99%A8%E7%BB%84%E4%BB%B6%E5%92%8C%E6%9C%8D%E5%8A%A1%E7%BB%84%E4%BB%B6.png)

服务器组件、服务组件主要就是取代我们人为的手动启动，将组件添加到Container里
当服务组件启动，Container里的全部组件就会启动

# 服务器组件
服务器组件就是实现了`Server`接口的类。当启动服务器组件后，它会启动所有的服务组件，并监听8005端口，等待接收关闭容器指令。
## initialize
initialize()方法主要用于初始化服务组件，会调用每个服务组件的initialize()方法
```
public void initialize()
throws LifecycleException {
    if (initialized)
        throw new LifecycleException (
            sm.getString("standardServer.initialize.initialized"));
    initialized = true;
    // Initialize our defined Services
    for (int i = 0; i < services.length; i++) {
        services[i].initialize();
    }
}
```

## start
当服务器组件启动时，会调用start()方法，该方法会启动所有的服务组件，即Service
```
public void start() throws LifecycleException {
    // Validate and update our current component state
    if (started)
        throw new LifecycleException
            (sm.getString("standardServer.start.started"));
    // Notify our interested LifecycleListeners
    lifecycle.fireLifecycleEvent(BEFORE_START_EVENT, null);
    lifecycle.fireLifecycleEvent(START_EVENT, null);
    started = true;
    // Start our defined Services
    synchronized (services) {
        for (int i = 0; i < services.length; i++) {
            if (services[i] instanceof Lifecycle)
                ((Lifecycle) services[i]).start();
        }
    }
    // Notify our interested LifecycleListeners
    lifecycle.fireLifecycleEvent(AFTER_START_EVENT, null);
}
```

## await
监听8005端口，等待关闭命令

# 服务组件
服务组件，即StandardService通常会包含两种组件，一种是连接器，一种是Servlet容器。一个Service组件会包含多个连接器，一个Servlet容器。
