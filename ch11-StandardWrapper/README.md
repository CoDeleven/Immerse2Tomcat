《深入剖析Tomcat》第十章

# 概要
本章主要对Wrapper接口得标准实现进行了说明，主要是拿StandardWrapper和StandardWrapperValve两个类来说明的。
对于每个Http请求，连接器都会调用与其关联容器的invoke()方法，最终StandardWrapperValve通过allocate()创建Servlet,最后调用service()执行Servlet。

![](https://blog-1252749790.file.myqcloud.com/tomcat/StandardWrapperFlowChart.png)

# Servlet初始化要点
Serlvet的初始化主要是由对应的Wrapper负责的，当StandardWrapperValve执行invoke()方法时，会调用Wrapper的allocate()方法来创建Servlet，创建过程中会根据singleThreadModel有不同的生成策略：
## SingleThreadModel
保证不会有两个线程执行同一个Servlet实例的标志接口（只是起到标记作用的接口）。注意该接口不保证线程安全性，只是能让Wrapper实现一个线程一个实例的作用。实现方式：
Wrapper在allocate()之后弹出并返回一个实例，然后通过nInstances控制数量。

## 当Servlet **没有实现** SingleThreadModel接口时
```java
// 一开始时通常是false
if (!singleThreadModel) {
    // 双检查机制创建Servlet，保证不会发生同步安全问题
    if (instance == null) {
        synchronized (this) {
            if (instance == null) {
                try {
                    instance = loadServlet();
                } catch (ServletException e) {
                    throw e;
                } catch (Throwable e) {
                    throw new ServletException
                        (sm.getString("standardWrapper.allocate"), e);
                }
            }
        }
    }
    // 如果loadServlet里创建的Servlet是实现了SingleThreadModel的，那么singleThreadModel就会为true。如果实例化的Servlet不是实现了singleThreadModel接口的Servlet 就可以 直接返回了
    if (!singleThreadModel) {
        if (debug >= 2)
            log("  Returning non-STM instance");
        countAllocated++;
        return (instance);
    }
}
```

在loadServlet方法里面，前面的内容基本就是类加载器去加载Servlet实例的过程；以下代码是改变singleThreadModel的代码片段，如果是singleThreadModel就创建一个instancePool。
```java
// loadSerlvet()
... 
singleThreadModel = servlet instanceof SingleThreadModel;
if (singleThreadModel) {
    if (instancePool == null)
        instancePool = new Stack();
}
...
```

## 当Servlet **实现** SingleThreadModel接口时
给每一个调用该方法的线程返回一个实例，若实例数量超过maxInstances就会进入wait()，每一次分配成功后，递增countAllocated。
```java
synchronized (instancePool) {
    while (countAllocated >= nInstances) {
        // Allocate a new instance if possible, or else wait
        if (nInstances < maxInstances) {
            try {
                instancePool.push(loadServlet());
                nInstances++;
            } catch (ServletException e) {
                throw e;
            } catch (Throwable e) {
                throw new ServletException
                    (sm.getString("standardWrapper.allocate"), e);
            }
        } else {
            try {
                instancePool.wait();
            } catch (InterruptedException e) {
                ;
            }
        }
    }
    if (debug >= 2)
        log("  Returning allocated STM instance");
    countAllocated++;
    return (Servlet) instancePool.pop();
}
```

## 总结
当调用Wrapper.allocate()时，如果该Servlet实现了SingleThreadModel接口，就会创建一个栈来存放同种类型的Servlet实例，每次调用allocate时，直接从对象池里弹出一个。如果该Servlet未实现SingleThreadModel接口，就会返回同一个对象实例，所以在多线程情况下会发生线程安全性问题。


# StandardWrapperFacade
一个门面类，用于在调用service.init()时传入的ServletConfig的具体实现。
具体实现可查看StandardWrapperFacade


# StandardWrapperValve
该阀主要做了两件事：
1. 调用该类所需要处理的所有过滤器
2. 调用实例的service()

## 过滤器处理
1. 创建Servlet
2. createFilterChain()创建过滤器链
3. 调用doFilter()，其中会执行到service()方法
4. 释放过滤器链

### doFilter 之 过滤器调用链
下面的代码是internalDoFilter()内的（篇幅过长，这里放要点）。先判断是否有其他的过滤器，如果有，则递归调用（因为在Filter编写中，我们会被要求写chain.doFilter()即重新调用doFilter()方法，如果不重新调用chaine.doFilter()，Servlet会收不到请求）。没有多余的过滤器，则直接跳过。
```java
// 过滤器递归调用
if (this.iterator.hasNext()) {
    ApplicationFilterConfig filterConfig =
      (ApplicationFilterConfig) iterator.next();
    Filter filter = null;
    try {
        // 注意这个
        filter = filterConfig.getFilter();
        support.fireInstanceEvent(InstanceEvent.BEFORE_FILTER_EVENT,
                filter, request, response);
        // 传入本身，方便Filter重新调用该ApplicationFilterChain.doFilter()
        filter.doFilter(request, response, this);
        support.fireInstanceEvent(InstanceEvent.AFTER_FILTER_EVENT,
                                  filter, request, response);
    } catch (IOException e) {
        if (filter != null)
            support.fireInstanceEvent(InstanceEvent.AFTER_FILTER_EVENT,
                                      filter, request, response, e);
        throw e;
    } catch (ServletException e) {
        if (filter != null)
            support.fireInstanceEvent(InstanceEvent.AFTER_FILTER_EVENT,
                                      filter, request, response, e);
        throw e;
    } catch (RuntimeException e) {
        if (filter != null)
            support.fireInstanceEvent(InstanceEvent.AFTER_FILTER_EVENT,
                                      filter, request, response, e);
        throw e;
    } catch (Throwable e) {
        if (filter != null)
            support.fireInstanceEvent(InstanceEvent.AFTER_FILTER_EVENT,
                                      filter, request, response, e);
        throw new ServletException
          (sm.getString("filterChain.filter"), e);
    }
    return;
}
```

### doFilter 之 ApplicationFilterConfig
每一个Filter会对应着一个FilterConfig，该类不需要我们手动生成，在我们配置web.xml里的Filter时，启动容器就立即加载创建。

最后在getFilter()内，会执行对应Filter的初始化。
```java
Filter getFilter() throws ClassCastException, ClassNotFoundException,
    IllegalAccessException, InstantiationException, ServletException {
    // Return the existing filter instance, if any
    if (this.filter != null)
        return (this.filter);
    // Identify the class loader we will be using
    String filterClass = filterDef.getFilterClass();
    ClassLoader classLoader = null;
    if (filterClass.startsWith("org.apache.catalina."))
        classLoader = this.getClass().getClassLoader();
    else
        classLoader = context.getLoader().getClassLoader();
    ClassLoader oldCtxClassLoader =
        Thread.currentThread().getContextClassLoader();
    // Instantiate a new instance of this filter and return it
    Class clazz = classLoader.loadClass(filterClass);
    this.filter = (Filter) clazz.newInstance();
    filter.init(this);
    return (this.filter);
}
```

这里的filterDef表示一个过滤器的定义，就是在web.xml里定义的<filter></filter>元素一样，不过filterDef是运行时的表现方式


### doFilter之执行service
当Filter调用完毕后，就开始执行Servlet.service()
```java
// 当过滤器调用完毕后，就开始执行Servlet.service()
support.fireInstanceEvent(InstanceEvent.BEFORE_SERVICE_EVENT,
                          servlet, request, response);
if ((request instanceof HttpServletRequest) &&
    (response instanceof HttpServletResponse)) {
    servlet.service((HttpServletRequest) request,
                    (HttpServletResponse) response);
} else {
    servlet.service(request, response);
}
support.fireInstanceEvent(InstanceEvent.AFTER_SERVICE_EVENT,
                          servlet, request, response);
```

## 过滤链的释放
在createFilterChain()方法里，会将所有的FilterConfig加入到ApplicationFilterChain里面，为了防止持续的引用FilterConfig、以及servlet对象等等，会调用filterChain的realease()。

```java
// invoke()
if (filterChain != null)
    filterChain.release();
```
```java
// release()
this.filters.clear();
// 不知道为啥自引用，如果是为了帮助GC，下面为什么不也设置成自引用
this.iterator = iterator;
this.servlet = null;
```

# 总结
在StandardWrapper里面，可以看到执行Servlet.service()之前会经过一层层的Filter。另外要注意Filter的递归过程。