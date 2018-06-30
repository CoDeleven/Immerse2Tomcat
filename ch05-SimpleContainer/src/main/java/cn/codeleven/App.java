package cn.codeleven;

import cn.codeleven.core.SimpleIPLogValve;
import cn.codeleven.core.SimpleWrapper;
import cn.codeleven.core.SimpleWrapperValve;
import org.apache.catalina.Contained;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Valve;
import org.apache.catalina.connector.http.HttpConnector;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        HttpConnector connector = new HttpConnector();

        SimpleLoader loader = new SimpleLoader();
        SimpleWrapper wrapper = new SimpleWrapper();
        wrapper.setServletClass("SimpleServlet");
        wrapper.setLoader(loader);

        wrapper.addValve(new SimpleIPLogValve(wrapper));
        SimpleWrapperValve wrapperValve = new SimpleWrapperValve(wrapper);
        wrapperValve.setContainer(wrapper);
        wrapper.setBasic(wrapperValve);

        connector.setContainer(wrapper);

        try {
            connector.initialize();
            connector.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
        System.in.read();
    }
}
