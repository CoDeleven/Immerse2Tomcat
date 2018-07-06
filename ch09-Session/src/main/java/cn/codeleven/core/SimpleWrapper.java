package cn.codeleven.core;

import org.apache.catalina.*;
import org.apache.catalina.util.LifecycleSupport;

import javax.naming.directory.DirContext;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * 不应该直接实现Pipeline得，理论上是继承BaseContainer得
 * Author: CoDeleven
 * Date: 2018/6/29
 */
public class SimpleWrapper implements Wrapper ,Pipeline, Lifecycle {
    private Loader loader;
    private Pipeline pipeline = new SimplePipeline();
    private Servlet instance;

    @Override
    public long getAvailable() {
        return 0;
    }

    @Override
    public void setAvailable(long available) {

    }

    @Override
    public String getJspFile() {
        return null;
    }

    @Override
    public void setJspFile(String jspFile) {

    }

    @Override
    public int getLoadOnStartup() {
        return 0;
    }

    @Override
    public void setLoadOnStartup(int value) {

    }

    @Override
    public String getRunAs() {
        return null;
    }

    @Override
    public void setRunAs(String runAs) {

    }

    @Override
    public String getServletClass() {
        return null;
    }
    private String servletClass;
    @Override
    public void setServletClass(String servletClass) {
        this.servletClass = servletClass;
    }

    @Override
    public boolean isUnavailable() {
        return false;
    }

    @Override
    public void addInitParameter(String name, String value) {

    }

    @Override
    public void addInstanceListener(InstanceListener listener) {

    }

    @Override
    public void addSecurityReference(String name, String link) {

    }

    @Override
    public Servlet allocate() throws ServletException {
        try {
            Class clazz = getLoader().getClassLoader().loadClass("cn.codeleven." + servletClass);
            if(clazz != null){
                return instance = (Servlet)clazz.newInstance();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deallocate(Servlet servlet) throws ServletException {
        instance.destroy();
    }

    @Override
    public String findInitParameter(String name) {
        return null;
    }

    @Override
    public String[] findInitParameters() {
        return new String[0];
    }

    @Override
    public String findSecurityReference(String name) {
        return null;
    }

    @Override
    public String[] findSecurityReferences() {
        return new String[0];
    }

    @Override
    public void load() throws ServletException {

    }

    @Override
    public void removeInitParameter(String name) {

    }

    @Override
    public void removeInstanceListener(InstanceListener listener) {

    }

    @Override
    public void removeSecurityReference(String name) {

    }

    @Override
    public void unavailable(javax.servlet.UnavailableException unavailable) {

    }

    @Override
    public void unload() throws ServletException {

    }
    private Container parent;
    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public Loader getLoader() {
        if(this.loader == null){
            return parent.getLoader();
        }
        return this.loader;
    }

    @Override
    public void setLoader(Loader loader) {
        this.loader = loader;
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

    private Logger logger;

    @Override
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Manager getManager() {
        return null;
    }

    @Override
    public void setManager(Manager manager) {

    }

    @Override
    public Cluster getCluster() {
        return null;
    }

    @Override
    public void setCluster(Cluster cluster) {

    }

    @Override
    public String getName() {
        return this.name;
    }
    private String name;
    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Container getParent() {
        return this.parent;
    }

    @Override
    public void setParent(Container container) {
        this.parent = container;
    }

    @Override
    public ClassLoader getParentClassLoader() {
        return null;
    }

    @Override
    public void setParentClassLoader(ClassLoader parent) {

    }

    @Override
    public Realm getRealm() {
        return null;
    }

    @Override
    public void setRealm(Realm realm) {

    }

    @Override
    public DirContext getResources() {
        return null;
    }

    @Override
    public void setResources(DirContext resources) {

    }

    @Override
    public void addChild(Container child) {

    }

    @Override
    public void addContainerListener(ContainerListener listener) {

    }

    @Override
    public void addMapper(Mapper mapper) {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public Container findChild(String name) {
        return null;
    }

    @Override
    public Container[] findChildren() {
        return new Container[0];
    }

    @Override
    public ContainerListener[] findContainerListeners() {
        return new ContainerListener[0];
    }

    @Override
    public Mapper findMapper(String protocol) {
        return null;
    }

    @Override
    public Mapper[] findMappers() {
        return new Mapper[0];
    }

    @Override
    public Valve getBasic() {
        return null;
    }

    @Override
    public void setBasic(Valve valve) {
        this.pipeline.setBasic(valve);
    }

    @Override
    public void addValve(Valve valve) {
        this.pipeline.addValve(valve);
    }

    @Override
    public Valve[] getValves() {
        return new Valve[0];
    }

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        this.pipeline.invoke(request, response);
    }

    @Override
    public void removeValve(Valve valve) {

    }

    @Override
    public Container map(Request request, boolean update) {
        return null;
    }

    @Override
    public void removeChild(Container child) {

    }

    @Override
    public void removeContainerListener(ContainerListener listener) {

    }

    @Override
    public void removeMapper(Mapper mapper) {

    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }


    @Override
    public void addLifecycleListener(LifecycleListener listener) {
        support.addLifecycleListener(listener);
    }

    @Override
    public LifecycleListener[] findLifecycleListeners() {
        return support.findLifecycleListeners();
    }

    @Override
    public void removeLifecycleListener(LifecycleListener listener) {
        support.removeLifecycleListener(listener);
    }

    @Override
    public void start() throws LifecycleException {
        System.out.println("Wrapper Name: SimpleWrapper 启动...");
        if (started) {
            throw new LifecycleException("已经执行start......");
        }

        started = true;
        support.fireLifecycleEvent(Lifecycle.BEFORE_START_EVENT, null);

        // 调用Loader的start
        ((Lifecycle) loader).start();


        if(logger instanceof Lifecycle){
            // 调用Logger
            ((Lifecycle) logger).start();
        }


        support.fireLifecycleEvent(Lifecycle.START_EVENT, null);
        support.fireLifecycleEvent(Lifecycle.AFTER_START_EVENT, null);
    }

    @Override
    public void stop() throws LifecycleException {
        System.out.println("Wrapper Name: SimpleWrapper 关闭...");

        if (!started) {
            throw new LifecycleException("尚未started......");
        }
        support.fireLifecycleEvent(Lifecycle.BEFORE_STOP_EVENT, null);

        if(instance != null){
            instance.destroy();
        }

        // 调用Loader的start
        ((Lifecycle) loader).stop();

        if(logger instanceof Lifecycle){
            // 调用Logger
            ((Lifecycle) logger).stop();
        }

        support.fireLifecycleEvent(Lifecycle.STOP_EVENT, null);
        support.fireLifecycleEvent(Lifecycle.AFTER_STOP_EVENT, null);
        started = false;
    }
    // this引用逸出
    private LifecycleSupport support = new LifecycleSupport(this);

    private boolean started;
}
