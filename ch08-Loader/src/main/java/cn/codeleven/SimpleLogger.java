package cn.codeleven;

import org.apache.catalina.*;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/30
 */
public class SimpleLogger implements Logger, Lifecycle {
    @Override
    public Container getContainer() {
        return null;
    }

    @Override
    public void setContainer(Container container) {

    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public int getVerbosity() {
        return 0;
    }

    @Override
    public void setVerbosity(int verbosity) {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public void log(String message) {
        System.out.println(message);
    }

    @Override
    public void log(Exception exception, String msg) {
        exception.printStackTrace();
    }

    @Override
    public void log(String message, Throwable throwable) {
        throwable.printStackTrace();

    }

    @Override
    public void log(String message, int verbosity) {

    }

    @Override
    public void log(String message, Throwable throwable, int verbosity) {

    }

    private List<LifecycleListener> lifecycleListeners = new ArrayList<>();

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public void addLifecycleListener(LifecycleListener listener) {
        lifecycleListeners.add(listener);
    }

    @Override
    public LifecycleListener[] findLifecycleListeners() {
        return lifecycleListeners.toArray(new LifecycleListener[0]);
    }

    @Override
    public void removeLifecycleListener(LifecycleListener listener) {
        lifecycleListeners.remove(listener);
    }

    @Override
    public void start() throws LifecycleException {

    }

    @Override
    public void stop() throws LifecycleException {

    }
}
