package cn.codeleven;

import cn.codeleven.core.*;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.http.HttpConnector;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) throws IOException, LifecycleException {
        SimpleContext context = new SimpleContext();

        context.setLogger(new SimpleLogger());

        context.addChild(getWrapper());

        // 添加阀
        context.addValve(new SimpleIPLogValve(context));
        // 添加基础阀
        context.setBasic(new SimpleContextValve(context));

        context.addMapper(new SimpleContextMapper(context));

        SimpleLoader loader = new SimpleLoader();

        context.setLoader(loader);

        HttpConnector connector = new HttpConnector();
        connector.setContainer(context);
        try {
            connector.initialize();
            connector.start();
            context.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
        // 里面以守护线程启动地
        System.in.read();
        context.stop();
    }

    private static Wrapper getWrapper() throws MalformedURLException {
        SimpleWrapper wrapper = new SimpleWrapper();
        wrapper.setServletClass("SimpleServlet");
        wrapper.setName("SimpleServlet");
        wrapper.setBasic(new SimpleWrapperValve(wrapper));
        wrapper.setLogger(new SimpleLogger());
        wrapper.setLoader(new SimpleLoader());
        return wrapper;
    }
}
