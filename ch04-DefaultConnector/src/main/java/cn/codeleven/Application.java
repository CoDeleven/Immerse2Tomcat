package cn.codeleven;

import cn.codeleven.connector.CustomConnector;
import org.apache.catalina.Container;
import org.apache.catalina.LifecycleException;

import java.io.File;
import java.io.IOException;

/**
 * Application
 * Author: CoDeleven
 * Date: 2018/6/18
 */
public class Application {

    public static void main(String[] args) throws IOException {
        CustomConnector connector = new CustomConnector();
        Container container = new SimpleContainer();

        connector.setContainer(container);

        try {
            connector.initialize();
            connector.start();
            new Thread(connector).start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
        System.in.read();
    }


}
