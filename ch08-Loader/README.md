《深入剖析Tomcat》第8章

# 概要
如果一个Servlet不加以限制，使其可以随意访问CLASSPATH环境下的所有类，这种行为是极其危险的（不知道如何判断其危险，觉得Servlet是程序员自己编写的，如果不执行客户端发送的文件，我觉得没什么大问题）。

为了限制一个Servlet的访问，我们通常采用自定义的类加载器来加载Servlet，为什么要如此设置呢？主要是因为java的双亲委派模型：

java内默认设置有三种模型：启动类加载器（用于启动JVM虚拟机）、扩展类加载器（加载放在ext目录下的类）、应用加载器（加载程序员编写的类）

应用类加载器加载程序员的类时，会先问启动类加载器，再问扩展类加载器，当顶部两者都没有加载过该类时，应用加载器就会加载该类。

而在Tomcat里，自定义的类加载器可以自由加入要访问的路径（这里的路径在Tomcat里被称为Classpath），这样所有的Web应用都会使用该classpath。

为什么要这么做呢？而且Spring等WEB框架为什么都支持 ** classpath:** 这样的操作呢？

记住一句话，约定大于配置。

这种方式可以省去很多时间

另一方面：
提供自动重载功能，即当WEB-INF/classes 或 WEB-INF/lib 目录下的类发生改变后，WEB应用会重新载入这些类

# 介绍

## WebappLoader
当容器启动时，会调用容器里所有组件的start()方法，而WebappLoader会完成以下几个工作：
* 创建一个类加载器（默认时WebappClassLoader)
* 设置仓库
* 设置类路径
* 设置访问权限
* 启动一个新线程用于发现内容的改变

有的同学可能注意到WebappClassLoader和WebappLoader两个加载器了，前者实现了Reload接口，后者实现了Load接口。
言外之意就是，Container使用时会使用WebappLoader加载器，而WebappLoader会具体使用WebappClassLoader类加载器来加载内容。

通俗点讲，就是WebappLoader会负责统筹全局，而WebappClassLoader负责具体加载业务。

```
try {
    // 如果没有进行设置，默认创建WebappClassLoader
    classLoader = createClassLoader();
    classLoader.setResources(container.getResources());
    classLoader.setDebug(this.debug);
    // 是否委托给父类载入器
    classLoader.setDelegate(this.delegate);
    for (int i = 0; i < repositories.length; i++) {
        classLoader.addRepository(repositories[i]);
    }
    // 设置要访问的类路径
    setRepositories();
    setClassPath();
    setPermissions();
    if (classLoader instanceof Lifecycle)
        ((Lifecycle) classLoader).start();
    // Binding the Webapp class loader to the directory context
    DirContextURLStreamHandler.bind
        ((ClassLoader) classLoader, this.container.getResources());
} catch (Throwable t) {
    throw new LifecycleException("start: ", t);
}
```

启动一个子线程用于判断指定目录是否有类发生变化
```
// 判断是否加载
if (reloadable) {
    log(sm.getString("webappLoader.reloading"));
    try {
        // 启动线程
        threadStart();
    } catch (IllegalStateException e) {
        throw new LifecycleException(e);
    }
}
```
新线程里面，会每隔一段时间判断一波是否需要reload，如果需要reload，那么就通知Container进行重加载

## WebappClassLoader
相关业务的设置和类加载

# 总结
Container通过Loader接口去实现自己的类加载器（和Java意义上的类加载器不同，这个类加载器会有自己的Permission、resources等等，还支持重新加载）

* 为什么要实现两个Loader？

因为单一职责。
* 设计模式思路？

觉得像是代理模式，又像是组合

* Classpath？

java里的Classpath需要在启动时进行设置；而Tomcat里的Classpath指WEB-INF下的目录，这种区别主要由自定义类加载器产生的。

* DocBase

设置放置资源的基础路径，可以理解为所有Servlet的源文件的集中地

* AppBase

所有Web应用存放的根目录；可以理解为DocBase为AppBase的子集
