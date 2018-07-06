package cn.codeleven.core;

import org.apache.catalina.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/30
 */
public class SimpleContextMapper implements Mapper, Lifecycle {
    private Container container;
    private String protocol;

    public SimpleContextMapper(Container container) {
        this.container = container;
    }

    @Override
    public Container getContainer() {
        return this.container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public String getProtocol() {
        return null;
    }

    @Override
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public Container map(Request request, boolean update) {
        String requestURI = ((HttpServletRequest)request.getRequest()).getRequestURI();
        int lastSlashIndex = requestURI.lastIndexOf('/');
        String name = requestURI.substring(lastSlashIndex + 1);
        if(name != null){
            return getContainer().findChild(name);
        }
        return null;
    }
    private List<LifecycleListener> lifecycleListeners = new ArrayList<>();

    @Override
    public void addLifecycleListener(LifecycleListener listener) {
        this.lifecycleListeners.add(listener);
    }

    @Override
    public LifecycleListener[] findLifecycleListeners() {
        return this.lifecycleListeners.toArray(new LifecycleListener[0]);
    }

    @Override
    public void removeLifecycleListener(LifecycleListener listener) {
        this.lifecycleListeners.remove(listener);
    }

    @Override
    public void start() throws LifecycleException {
        System.out.println("SimpleContextMapper started...");
    }

    @Override
    public void stop() throws LifecycleException {
        System.out.println("SimpleContextMapper stopped...");
    }

}
