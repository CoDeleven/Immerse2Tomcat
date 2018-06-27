package cn.codeleven.connector;


import cn.codeleven.processor.HttpProcessor;
import org.apache.catalina.*;
import org.apache.catalina.net.DefaultServerSocketFactory;
import org.apache.catalina.net.ServerSocketFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Stack;
import java.util.stream.IntStream;

/**
 * 该类不符合后面版本的Tomcat
 * 后面版本的Tomcat使用的Connector是一个类
 * <p>
 * Author: CoDeleven
 * Date: 2018/6/26
 */
public class CustomConnector implements Connector, Lifecycle, Runnable {
    private ServerSocket serverSocket;
    private int maxProcessors;
    private int coreProcessors;
    private ServerSocketFactory factory;
    private Stack<HttpProcessor> processorPool = new Stack();
    private int currentProcessor = 0;
    private Container container;


    @Override
    public Container getContainer() {
        return this.container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public boolean getEnableLookups() {
        return false;
    }

    @Override
    public void setEnableLookups(boolean enableLookups) {

    }

    @Override
    public ServerSocketFactory getFactory() {
        return null;
    }

    @Override
    public void setFactory(ServerSocketFactory factory) {

    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public int getRedirectPort() {
        return 0;
    }

    @Override
    public void setRedirectPort(int redirectPort) {

    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public void setScheme(String scheme) {

    }

    @Override
    public boolean getSecure() {
        return false;
    }

    @Override
    public void setSecure(boolean secure) {

    }

    @Override
    public Service getService() {
        return null;
    }

    @Override
    public void setService(Service service) {

    }

    @Override
    public Request createRequest() {
        return null;
    }

    @Override
    public Response createResponse() {
        return null;
    }

    @Override
    public void initialize() throws LifecycleException {
        factory = open();
        try {
            this.serverSocket = factory.createSocket(8080);
        } catch (IOException | CertificateException | KeyManagementException | KeyStoreException | UnrecoverableKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addLifecycleListener(LifecycleListener listener) {

    }

    /**
     * 获取一个ServerSocketFactory
     */
    protected ServerSocketFactory open() {
        return new DefaultServerSocketFactory();
    }

    @Override
    public LifecycleListener[] findLifecycleListeners() {
        return new LifecycleListener[0];
    }

    @Override
    public void removeLifecycleListener(LifecycleListener listener) {

    }

    @Override
    public void start() throws LifecycleException {
        IntStream.range(currentProcessor, coreProcessors).forEach((intValue) -> {
            HttpProcessor processor = new HttpProcessor(getContainer());
            recycle(processor);
        });
    }

    @Override
    public void stop() throws LifecycleException {

    }

    protected void recycle(HttpProcessor processor) {
        this.processorPool.push(processor);
    }

    @Override
    public void run() {
        while(true){
            try {
                Socket socket = this.serverSocket.accept();
                HttpProcessor processor = createProcessor();

                if(processor == null){
                    socket.close();
                    continue;
                }
                // 交给处理器处理
                processor.assign(socket);
                // 回收
                recycle(processor);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private HttpProcessor createProcessor() {
        if(processorPool.empty()){
            if(currentProcessor >= maxProcessors){
                return null;
            }
            HttpProcessor processor = new HttpProcessor(this.getContainer());
            ++currentProcessor;
            return processor;
        }else{
            return processorPool.pop();
        }
    }
}
