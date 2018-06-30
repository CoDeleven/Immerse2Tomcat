package cn.codeleven;

import org.apache.catalina.Container;
import org.apache.catalina.Logger;

import java.beans.PropertyChangeListener;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/30
 */
public class SimpleLogger implements Logger {
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

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }
}
