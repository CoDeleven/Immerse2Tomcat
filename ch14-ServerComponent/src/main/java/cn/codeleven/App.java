package cn.codeleven;


import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.*;
import org.apache.catalina.loader.WebappLoader;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws LifecycleException {
        // 设置环境catalina.base的目录
        System.setProperty("catalina.base", System.getProperty("user.dir"));
        // 创建连接器
        Connector connector = new HttpConnector();
        Wrapper wrapper1 = new StandardWrapper();
        wrapper1.setName("Primitive");
        wrapper1.setServletClass("PrimitiveClass");

        Wrapper wrapper2 = new StandardWrapper();
        wrapper2.setName("Modern");
        wrapper2.setServletClass("ModernServlet");
        // 当前应用的应用目录
        Context context = new StandardContext();
        context.setPath("/app1");
        context.setDocBase("app1");
        context.addChild(wrapper1);
        context.addChild(wrapper2);

        LifecycleListener listener = new SimpleContextConfig();
        ((StandardContext) context).addLifecycleListener(listener);

        // 创建一个主机
        Host host = new StandardHost();
        host.addChild(context);
        host.setName("Localhost");
        // 所有的应用目录
        host.setAppBase("webapps");

        Loader loader = new WebappLoader();
        context.setLoader(loader);
        context.addServletMapping("/Primitive", "Primitive");
        context.addServletMapping("/Modern", "Modern");

        // 创建一个引擎
        Engine engine = new StandardEngine();
        engine.addChild(host);
        engine.setDefaultHost("localhost");

        // 创建服务组件
        Service service = new StandardService();
        service.setName("Stand-Alone Service");
        // 添加一个连接器
        service.addConnector(connector);
        // 添加一个容器
        service.setContainer(engine);


        // 创建服务器组件
        Server server = new StandardServer();
        server.addService(service);

        // 只需要调用server.initialize()
        server.initialize();
        // 启动server里面的全部组件
        ((StandardServer) server).start();
        // 让server进入等待
        server.await();

        ((StandardServer) server).stop();
    }

}
