《深入剖析Tomcat》第十二章

# 概要
本章主要讲StandardContext的实例化和配置，然后讨论StandardContext相关的StandardContextMapper及ContextConfig。

# StandardContext
StandardContext的start()流程主要分为以下几个：
* 配置资源
* 设置载入器
* 设置Session管理器
* 初始化字符集映射器
* 设置工作目录
* 启动与该Context容器相关联的组件
* BindThread
* 启动子容器
* 触发START事件
* 检查configured值
* 触发AFTER_START事件

由于启动的过程比较长，这里挑几个重点来讲讲。
## 配置资源
```java
if (getResources() == null) {   // (1) Required by Loader
    if (debug >= 1)
        log("Configuring default Resources");
    try {
        if ((docBase != null) && (docBase.endsWith(".war")))
            setResources(new WARDirContext());
        else
            setResources(new FileDirContext());
    } catch (IllegalArgumentException e) {
        log("Error initializing resources: " + e.getMessage());
        ok = false;
    }
}
```
通过判断docBase（存放所有WEBAPP的根目录）是否以*.war*结尾，从而设置不同的环境

## 设置工作目录
postWorkDirectory()方法是用来创建工作目录的方法，通常我们在运行一个AppWeb的时候会创建一个work工作目录，该目录就是由该方法生成的。

## bindThread
这一小节主要涉及到的是Tomcat的JNDI篇，我们可以看该链接
[Tomcat相关架构文章](http://gearever.iteye.com/)

# StandardMapper
当StandardContextValve要调用对应的Servlet时，先通过获取context，然后通过context.map()获取对应的Wrapper

```java
// 比如前面会有这样的操作
context.addServletMapping("/xxx", "xxx");
context.addServletMapping("/Modern", "Modern");
context.addChild(wrapper1);
context.addChild(wrapper2);
```
所以在这里Mapper是起到一个 **通过pattern** 查找到对应Wrapper的作用，这里的查找过程主要分为三段：
* 精确匹配
* 前缀匹配
* 扩展名匹配
* 默认匹配

# 重载
这个过程主要是通过为WebappLoader设置Container时触发的：
```java
public void setContainer(Container container) {
    // Deregister from the old Container (if any)
    if ((this.container != null) && (this.container instanceof Context))
        ((Context) this.container).removePropertyChangeListener(this);
    // Process this property change
    Container oldContainer = this.container;
    this.container = container;
    support.firePropertyChange("container", oldContainer, this.container);
    // Register with the new Container (if any)
    if ((this.container != null) && (this.container instanceof Context)) {
        setReloadable( ((Context) this.container).getReloadable() );
        ((Context) this.container).addPropertyChangeListener(this);
    }
}
```
先获取Container的reloadable，然后为自己设置reloadable，此时就会启动一个线程来检查WEB-INF目录下的更新时间戳。