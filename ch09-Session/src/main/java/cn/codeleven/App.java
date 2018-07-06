package cn.codeleven;

import cn.codeleven.core.*;
import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.loader.WebappClassLoader;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.logger.SystemErrLogger;
import org.apache.catalina.session.StandardManager;
import org.apache.naming.resources.ProxyDirContext;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, LifecycleException {
        System.setProperty("catalina.base", System.getProperty("user.dir"));

        System.out.println(System.getProperty("user.dir"));

        StandardContext context = new StandardContext();

        context.setLogger(new SystemErrLogger());
        context.addChild(getWrapper(context));

        context.setManager(new StandardManager());

        context.setPath("/myapp");
        context.setDocBase("myapp");

        // 需要设置监听器，在开始的时候，为Container设置configured为true
        // 否则在start过程中就会被stop
        LifecycleListener listener = new SimpleContextConfig();
        ((Lifecycle) context).addLifecycleListener(listener);

        // 添加阀
        context.addValve(new SimpleIPLogValve(context));
        // 添加基础阀
        context.setBasic(new SimpleContextValve(context));

        context.addMapper(new SimpleContextMapper(context));

        WebappLoader loader = new WebappLoader();

        context.setLoader(loader);

        HttpConnector connector = new HttpConnector();
        connector.setContainer(context);
        try {
            connector.initialize();
            connector.start();
            context.start();

            WebappClassLoader classLoader = (WebappClassLoader) loader.getClassLoader();
            System.out.println("Resources' docBase: " + ((ProxyDirContext)classLoader.getResources()).getDocBase());
            String[] repositories = classLoader.findRepositories();
            for (int i=0; i<repositories.length; i++) {
                System.out.println("  repository: " + repositories[i]);
            }
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
        // 里面以守护线程启动地
        System.in.read();
        context.stop();

    }
    private static Wrapper getWrapper(Container parent) throws MalformedURLException {
        SimpleWrapper wrapper = new SimpleWrapper();
        wrapper.setServletClass("SimpleServlet");
        wrapper.setName("SimpleServlet");
        wrapper.setParent(parent);
        wrapper.setBasic(new SimpleWrapperValve(wrapper));
        wrapper.setLogger(new SimpleLogger());
        wrapper.setLoader(new SimpleLoader());
        return wrapper;
    }}
