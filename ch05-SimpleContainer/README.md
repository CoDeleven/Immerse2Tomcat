《深入剖析Tomcat》第五章


# 概要
本章主要讲解Servlet容器，Servlet容器是用来处理Servlet资源，为Web客户端填充Response对象得模块。
在Tomcat4里，共有四种容器：

* Engine：整个Catalina引擎
* Host：包含一个或多个Context容器得虚拟主机
* Context：表示一个web应用程序。一个Context可以有多个Wrapper
* Wrapper：表示一个独立得Servlet

一个容器可以有0个或多个低层级得子容器，Wrapper处于层级结构得最底层（无法再包含更多子容器）。

![](https://blog-1252749790.file.myqcloud.com/tomcat/Tomcat_Container.png)


在Tomcat4里面，Engine的实现类是StandardEngine；Host的实现类是StandardHost；Context的实现类是StandardContext；Wrapper的实现类是StandardWrapper。由于篇幅的原因只取前两个的继承关系作为例子：

![](https://blog-1252749790.file.myqcloud.com/tomcat/StandardHost_uml.png)

![](https://blog-1252749790.file.myqcloud.com/tomcat/StandardEngine_uml.png)


每个容器（包括StandardWrapper）都会继承`BaseContainer`这个类（针对Tomcat4），然后实现一个属于自己的接口。

# 管道和阀
该模块可以理解为Servlet的过滤器，当客户端发送一个请求时，会经过一层层过滤器筛选。管道和阀也是同样的思想：

![](https://blog-1252749790.file.myqcloud.com/tomcat/pipeline_valve.png)

管道是由一系列的阀组成的，一个请求经过所有的阀后最终会到达基础阀，基础阀只有一个，该阀通常用于转发请求给对应的容器。

```java
// 调用Container.invoke() --> 调用该Container的Pipeline.invoke() 
// Pipeline.invoke() --> Pipeline.StandardPipelineValveContext.invokeNext();
// 该方法是Pipeline.StandardPipelineValveContext.invokeNext()
// 调用全部的Valve
public void invokeNext(Request request, Response response)
    throws IOException, ServletException {
    int subscript = stage;
    stage = stage + 1;
    // Invoke the requested Valve for the current request thread
    if (subscript < valves.length) {
        valves[subscript].invoke(request, response, this);
    } else if ((subscript == valves.length) && (basic != null)) {
        basic.invoke(request, response, this);
    } else {
        throw new ServletException
            (sm.getString("standardPipeline.noValve"));
    }
}

```


# 误区
~~Container接口得设计满足以下条件：在部署应用时，Tomcat管理员可以通过编辑配置文件（server.xml)来决定使用哪种容器，这是通过管道和阀的集合实现的（不是很理解实现）。~~

上面的句子出自《深入剖析Tomcat》第五章。笔者通过阅读源码，发现 **决定使用哪个容器并不是通过管道和阀的集合来实现的**并不准确，确切来说是通过映射对应的关键字实现的（但是是通过管道和阀的接口来调用的）：
```java
// StandardEngineValue.invoke()
public void invoke(Request request, Response response,
                   ValveContext valveContext)
    throws IOException, ServletException {
    // Validate the request and response object types
    if (!(request.getRequest() instanceof HttpServletRequest) ||
        !(response.getResponse() instanceof HttpServletResponse)) {
        return;     // NOTE - Not much else we can do generically
    }
    // Validate that any HTTP/1.1 request included a host header
    HttpServletRequest hrequest = (HttpServletRequest) request;
    if ("HTTP/1.1".equals(hrequest.getProtocol()) &&
        (hrequest.getServerName() == null)) {
        ((HttpServletResponse) response.getResponse()).sendError
            (HttpServletResponse.SC_BAD_REQUEST,
             sm.getString("standardEngine.noHostHeader",
                          request.getRequest().getServerName()));
        return;
    }
    // Select the Host to be used for this Request
    StandardEngine engine = (StandardEngine) getContainer();
    // 通过映射，找到符合请求的Host
    Host host = (Host) engine.map(request, true);
    if (host == null) {
        ((HttpServletResponse) response.getResponse()).sendError
            (HttpServletResponse.SC_BAD_REQUEST,
             sm.getString("standardEngine.noHost",
                          request.getRequest().getServerName()));
        return;
    }
    // 继续调用符合请求的Host的invoke()
    host.invoke(request, response);
}
```

