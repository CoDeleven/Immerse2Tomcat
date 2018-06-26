本章对应《深入剖析Tomcat》的第三章内容

主要做了以下几件事：
1. 添加了两个模块：Connector和Processor，前者用于接收请求、后者用于处理请求和响应。将接收和处理进行解耦。
2. Connector目前为止只是简单的接收请求，并转发给Processor进行处理
3. Processor目前为止对Request进行解析，包括请求行、请求头、Cookie等相关处理，Response没有进行过多的处理
4. Response对getWriter()方法进行了处理，将SocketOutputStream封装为ServletOutputStream，在其中进行了第一次的头处理

书内有许多地方比较模糊，个别地方是笔者自行脑补的。

优化：
相比第二阶段的内容，模块间的关系更加清晰了；而且处理的内容更加完善（处理请求行、请求头等），总而言之，本章就是完善了Request的解析。

Update1:

更新Request和Response：
1. 逻辑更加完善
2. 理清ResponseStream和PrintWriter
3. 理清SocketInputStream和RequestStream

遗留的问题：
1. 目前看来PrintWriter似乎没什么用处，最多就是print后自动刷新
2. 为什么项目不直接采用String而是用char[]数组

![uml图](http://blog-1252749790.cossh.myqcloud.com/Immerse2Tomcat_ch03.png)