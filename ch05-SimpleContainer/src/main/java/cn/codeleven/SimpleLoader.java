package cn.codeleven;

import org.apache.catalina.Container;
import org.apache.catalina.DefaultContext;
import org.apache.catalina.Loader;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/29
 */
public class SimpleLoader implements Loader {
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
    private ClassLoader loader;
    private Container container;

    public SimpleLoader() throws MalformedURLException {
        String repositoryUrl = new URL("file", null, WEB_ROOT + File.separator).toString();
        loader = new URLClassLoader(new URL[]{new URL(repositoryUrl)});
    }

    @Override
    public ClassLoader getClassLoader() {
        return loader;
    }

    @Override
    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public DefaultContext getDefaultContext() {
        return null;
    }

    @Override
    public void setDefaultContext(DefaultContext defaultContext) {

    }

    @Override
    public boolean getDelegate() {
        return false;
    }

    @Override
    public void setDelegate(boolean delegate) {

    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public boolean getReloadable() {
        return false;
    }

    @Override
    public void setReloadable(boolean reloadable) {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public void addRepository(String repository) {

    }

    @Override
    public String[] findRepositories() {
        return new String[0];
    }

    @Override
    public boolean modified() {
        return false;
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }
}
