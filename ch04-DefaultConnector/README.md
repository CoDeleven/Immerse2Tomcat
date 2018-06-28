本章对应《深入剖析Tomcat》的第四章内容

主要做了以下几件事
1. 添加了Container模块，一个动态分析Servlet的容器，由它动态分配对应的Servlet
2. 完善Connector 使它支持多线程提交任务



总结
本章应用只让做一个Container。
整体的架构是 Bootstrap -> Connector -> Processor -> Container

* Bootstrap启动Connector
* Connector负责处理连接请求，在这里可以对其进行优化，第一个版本是单线程同步阻塞处理，第二个版本是多线程异步非阻塞处理？（感觉没有回调，就是把东西交给processor就继续做自己的事了）
* Container负责动态处理请求内容，分配Servlet