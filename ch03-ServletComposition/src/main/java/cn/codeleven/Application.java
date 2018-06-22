package cn.codeleven;

import cn.codeleven.connector.HttpConnector;
import cn.codeleven.processor.HttpProcessor;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Application
 * Author: CoDeleven
 * Date: 2018/6/18
 */
public class Application {
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";

    public static void main(String[] args) throws IOException {
        HttpConnector connector = new HttpConnector();
        connector.start();
    }


}
