本章对应《深入剖析Tomcat》的第三章内容

主要做了以下几件事：
1. 添加了两个模块：Connector和Processor，前者用于接收请求、后者用于处理请求和响应。将接收和处理进行解耦。
2. Connector目前为止只是简单的接收请求，并转发给Processor进行处理
3. Processor目前为止对Request进行解析，包括请求行、请求头、Cookie等相关处理，Respose没有进行过多的处理
4. Response对getWriter()方法进行了处理，将SocketOutputStream封装为ServletOutputStream，在其中进行了第一次的头处理

书内有许多地方比较模糊，个别地方是笔者自行脑补的。

优化：
相比第二阶段的内容，模块间的关系更加清晰了；而且处理的内容更加完善（处理请求行、请求头等），总而言之，本章就是完善了Request的解析。