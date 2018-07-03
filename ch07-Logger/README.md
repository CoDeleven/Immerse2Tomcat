《深入剖析Tomcat》第七章

# 概要

本章内容主要是介绍Tomcat的日志工具，所有的日志工具均需要实现Logger接口，Apache提供了LoggerBase抽象类，及SystemOutLogger、SystemErrLogger和FileLogger三个具体实现类：

* SystemOutLogger: 使用System.out输出的Logger
* SystemErrLogger: 使用System.err输出的Logger
* FileLogger: 顾名思义，是使用文件来记录日志信息的Logger


# LoggerBase
其中比较重要的逻辑在于Log()方法内，首先是对level的判断，只有大于等于当前等级的日志才会被输出，比如我设置的日志等级为ERROR，那么只有大于ERROR等级的日志消息才会被输出。
VERBOSE < INFORMATION < WARNING < ERROR < FATAL

其次是对包含throwable参数的方法进行的特殊处理

# FileLogger
FileLogger是特殊的，也是唯一一个实现Lifecycle接口的日志工具。
里面特别要注意的方法有如下几个：
```
open()

private void open() {
    // 创建文件夹
    File dir = new File(directory);
    // 判断路径是否是绝对路径，windows下绝对路径是以盘符开头，linux下是/开头
    if (!dir.isAbsolute())
        // catalina.base是bin、conf目录的父目录
        // 暗示如果不是绝对路径，就把directory放在tomcat根目录下
        dir = new File(System.getProperty("catalina.base"), directory);
    // 创建多层文件夹
    dir.mkdirs();
    // Open the current log file
    try {
        String pathname = dir.getAbsolutePath() + File.separator +
            prefix + date + suffix;
        // 包装到PrintWriter
        writer = new PrintWriter(new FileWriter(pathname, true), true);
    } catch (IOException e) {
        writer = null;
    }
}
```

```
close()
// 刷新并关闭流
private void close() {
    if (writer == null)
        return;
    writer.flush();
    writer.close();
    writer = null;
    date = "";
}
```

```
stop()

public void stop() throws LifecycleException {
    // 如果生命周期没进入开始
    if (!started)
        throw new LifecycleException
            (sm.getString("fileLogger.notStarted"));
    lifecycle.fireLifecycleEvent(STOP_EVENT, null);
    started = false;
    close();
}
```

总而言之，Logger相关的类都比较简单，没有性能方面的考虑，只是简单的用于日志输出。

# 总结
Tomcat4里只有FileLogger有实现Lifecycle。
Tomcat5，在LoggerBase里实现了Lifecycle，所以每个Logger实现类都会有各自的生命周期。