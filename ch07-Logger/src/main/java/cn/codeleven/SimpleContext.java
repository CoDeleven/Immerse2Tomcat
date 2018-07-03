package cn.codeleven;

import cn.codeleven.core.SimplePipeline;
import org.apache.catalina.*;
import org.apache.catalina.deploy.*;
import org.apache.catalina.util.CharsetMapper;
import org.apache.catalina.util.LifecycleSupport;

import javax.naming.directory.DirContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/30
 */
public class SimpleContext implements Context, Pipeline, Lifecycle {
    private Loader loader;
    private Pipeline pipeline = new SimplePipeline();
    private Logger logger;
    private Map<String, Container> childContainers = new HashMap<>();
    private List<Mapper> mappers = new ArrayList<>();

    // this引用逸出
    private LifecycleSupport support = new LifecycleSupport(this);

    private boolean started;

    @Override
    public Object[] getApplicationListeners() {
        return new Object[0];
    }

    @Override
    public void setApplicationListeners(Object[] listeners) {

    }

    @Override
    public boolean getAvailable() {
        return false;
    }

    @Override
    public void setAvailable(boolean available) {

    }

    @Override
    public CharsetMapper getCharsetMapper() {
        return null;
    }

    @Override
    public void setCharsetMapper(CharsetMapper mapper) {

    }

    @Override
    public boolean getConfigured() {
        return false;
    }

    @Override
    public void setConfigured(boolean configured) {

    }

    @Override
    public boolean getCookies() {
        return false;
    }

    @Override
    public void setCookies(boolean cookies) {

    }

    @Override
    public boolean getCrossContext() {
        return false;
    }

    @Override
    public void setCrossContext(boolean crossContext) {

    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public void setDisplayName(String displayName) {

    }

    @Override
    public boolean getDistributable() {
        return false;
    }

    @Override
    public void setDistributable(boolean distributable) {

    }

    @Override
    public String getDocBase() {
        return null;
    }

    @Override
    public void setDocBase(String docBase) {

    }

    @Override
    public LoginConfig getLoginConfig() {
        return null;
    }

    @Override
    public void setLoginConfig(LoginConfig config) {

    }

    @Override
    public NamingResources getNamingResources() {
        return null;
    }

    @Override
    public void setNamingResources(NamingResources namingResources) {

    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public void setPath(String path) {

    }

    @Override
    public String getPublicId() {
        return null;
    }

    @Override
    public void setPublicId(String publicId) {

    }

    @Override
    public boolean getReloadable() {
        return false;
    }

    @Override
    public void setReloadable(boolean reloadable) {

    }

    @Override
    public boolean getOverride() {
        return false;
    }

    @Override
    public void setOverride(boolean override) {

    }

    @Override
    public boolean getPrivileged() {
        return false;
    }

    @Override
    public void setPrivileged(boolean privileged) {

    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public int getSessionTimeout() {
        return 0;
    }

    @Override
    public void setSessionTimeout(int timeout) {

    }

    @Override
    public String getWrapperClass() {
        return null;
    }

    @Override
    public void setWrapperClass(String wrapperClass) {

    }

    @Override
    public void addApplicationListener(String listener) {

    }

    @Override
    public void addApplicationParameter(ApplicationParameter parameter) {

    }

    @Override
    public void addConstraint(SecurityConstraint constraint) {

    }

    @Override
    public void addEjb(ContextEjb ejb) {

    }

    @Override
    public void addEnvironment(ContextEnvironment environment) {

    }

    @Override
    public void addErrorPage(ErrorPage errorPage) {

    }

    @Override
    public void addFilterDef(FilterDef filterDef) {

    }

    @Override
    public void addFilterMap(FilterMap filterMap) {

    }

    @Override
    public void addInstanceListener(String listener) {

    }

    @Override
    public void addLocalEjb(ContextLocalEjb ejb) {

    }

    @Override
    public void addMimeMapping(String extension, String mimeType) {

    }

    @Override
    public void addParameter(String name, String value) {

    }

    @Override
    public void addResource(ContextResource resource) {

    }

    @Override
    public void addResourceEnvRef(String name, String type) {

    }

    @Override
    public void addResourceLink(ContextResourceLink resourceLink) {

    }

    @Override
    public void addRoleMapping(String role, String link) {

    }

    @Override
    public void addSecurityRole(String role) {

    }

    @Override
    public void addServletMapping(String pattern, String name) {

    }

    @Override
    public void addTaglib(String uri, String location) {

    }

    @Override
    public void addWelcomeFile(String name) {

    }

    @Override
    public void addWrapperLifecycle(String listener) {

    }

    @Override
    public void addWrapperListener(String listener) {

    }

    @Override
    public Wrapper createWrapper() {
        return null;
    }

    @Override
    public String[] findApplicationListeners() {
        return new String[0];
    }

    @Override
    public ApplicationParameter[] findApplicationParameters() {
        return new ApplicationParameter[0];
    }

    @Override
    public SecurityConstraint[] findConstraints() {
        return new SecurityConstraint[0];
    }

    @Override
    public ContextEjb findEjb(String name) {
        return null;
    }

    @Override
    public ContextEjb[] findEjbs() {
        return new ContextEjb[0];
    }

    @Override
    public ContextEnvironment findEnvironment(String name) {
        return null;
    }

    @Override
    public ContextEnvironment[] findEnvironments() {
        return new ContextEnvironment[0];
    }

    @Override
    public ErrorPage findErrorPage(int errorCode) {
        return null;
    }

    @Override
    public ErrorPage findErrorPage(String exceptionType) {
        return null;
    }

    @Override
    public ErrorPage[] findErrorPages() {
        return new ErrorPage[0];
    }

    @Override
    public FilterDef findFilterDef(String filterName) {
        return null;
    }

    @Override
    public FilterDef[] findFilterDefs() {
        return new FilterDef[0];
    }

    @Override
    public FilterMap[] findFilterMaps() {
        return new FilterMap[0];
    }

    @Override
    public String[] findInstanceListeners() {
        return new String[0];
    }

    @Override
    public ContextLocalEjb findLocalEjb(String name) {
        return null;
    }

    @Override
    public ContextLocalEjb[] findLocalEjbs() {
        return new ContextLocalEjb[0];
    }

    @Override
    public String findMimeMapping(String extension) {
        return null;
    }

    @Override
    public String[] findMimeMappings() {
        return new String[0];
    }

    @Override
    public String findParameter(String name) {
        return null;
    }

    @Override
    public String[] findParameters() {
        return new String[0];
    }

    @Override
    public ContextResource findResource(String name) {
        return null;
    }

    @Override
    public String findResourceEnvRef(String name) {
        return null;
    }

    @Override
    public String[] findResourceEnvRefs() {
        return new String[0];
    }

    @Override
    public ContextResourceLink findResourceLink(String name) {
        return null;
    }

    @Override
    public ContextResourceLink[] findResourceLinks() {
        return new ContextResourceLink[0];
    }

    @Override
    public ContextResource[] findResources() {
        return new ContextResource[0];
    }

    @Override
    public String findRoleMapping(String role) {
        return null;
    }

    @Override
    public boolean findSecurityRole(String role) {
        return false;
    }

    @Override
    public String[] findSecurityRoles() {
        return new String[0];
    }

    @Override
    public String findServletMapping(String pattern) {
        return null;
    }

    @Override
    public String[] findServletMappings() {
        return new String[0];
    }

    @Override
    public String findStatusPage(int status) {
        return null;
    }

    @Override
    public int[] findStatusPages() {
        return new int[0];
    }

    @Override
    public String findTaglib(String uri) {
        return null;
    }

    @Override
    public String[] findTaglibs() {
        return new String[0];
    }

    @Override
    public boolean findWelcomeFile(String name) {
        return false;
    }

    @Override
    public String[] findWelcomeFiles() {
        return new String[0];
    }

    @Override
    public String[] findWrapperLifecycles() {
        return new String[0];
    }

    @Override
    public String[] findWrapperListeners() {
        return new String[0];
    }

    @Override
    public void reload() {

    }

    @Override
    public void removeApplicationListener(String listener) {

    }

    @Override
    public void removeApplicationParameter(String name) {

    }

    @Override
    public void removeConstraint(SecurityConstraint constraint) {

    }

    @Override
    public void removeEjb(String name) {

    }

    @Override
    public void removeEnvironment(String name) {

    }

    @Override
    public void removeErrorPage(ErrorPage errorPage) {

    }

    @Override
    public void removeFilterDef(FilterDef filterDef) {

    }

    @Override
    public void removeFilterMap(FilterMap filterMap) {

    }

    @Override
    public void removeInstanceListener(String listener) {

    }

    @Override
    public void removeLocalEjb(String name) {

    }

    @Override
    public void removeMimeMapping(String extension) {

    }

    @Override
    public void removeParameter(String name) {

    }

    @Override
    public void removeResource(String name) {

    }

    @Override
    public void removeResourceEnvRef(String name) {

    }

    @Override
    public void removeResourceLink(String name) {

    }

    @Override
    public void removeRoleMapping(String role) {

    }

    @Override
    public void removeSecurityRole(String role) {

    }

    @Override
    public void removeServletMapping(String pattern) {

    }

    @Override
    public void removeTaglib(String uri) {

    }

    @Override
    public void removeWelcomeFile(String name) {

    }

    @Override
    public void removeWrapperLifecycle(String listener) {

    }

    @Override
    public void removeWrapperListener(String listener) {

    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public Loader getLoader() {
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
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public Container getParent() {
        return null;
    }

    @Override
    public void setParent(Container container) {

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
        this.childContainers.put(child.getName(), child);
    }

    @Override
    public void addContainerListener(ContainerListener listener) {

    }

    @Override
    public void addMapper(Mapper mapper) {
        this.mappers.add(mapper);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public Container findChild(String name) {
        // 查找符合该名字地子容器
        return this.childContainers.get(name);
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
        // 不想判断Mapper
        // 理论上这里找到对应协议地Mapper
        // 然后Mapper会查找具体地Servlet
        return mappers.get(0);
    }

    @Override
    public Mapper[] findMappers() {
        return new Mapper[0];
    }

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        this.pipeline.invoke(request, response);
    }

    @Override
    public Container map(Request request, boolean update) {
        String protocol = request.getRequest().getProtocol();

        Mapper mapper = findMapper(protocol);

        return mapper.map(request, update);
    }

    @Override
    public void removeChild(Container child) {

    }

    @Override
    public void removeContainerListener(ContainerListener listener) {

    }

    @Override
    public void removeMapper(Mapper mapper) {
        this.mappers.remove(mapper);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public Valve getBasic() {
        return this.pipeline.getBasic();
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
    public void removeValve(Valve valve) {
        this.pipeline.removeValve(valve);
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
        if (started) {
            throw new LifecycleException("已经执行start......");
        }

        started = true;
        support.fireLifecycleEvent(Lifecycle.BEFORE_START_EVENT, null);

        // 调用Loader的start
        ((Lifecycle) loader).start();

        // 调用Mapper的start
        for (Mapper mapper : mappers) {
            ((Lifecycle) mapper).start();
        }
        if(logger instanceof Lifecycle){
            // 调用Logger
            ((Lifecycle) logger).start();
        }


        // 调用Container
        for (Container container : childContainers.values()) {
            ((Lifecycle) container).start();
        }

        support.fireLifecycleEvent(Lifecycle.START_EVENT, null);
        support.fireLifecycleEvent(Lifecycle.AFTER_START_EVENT, null);
    }

    @Override
    public void stop() throws LifecycleException {
        if (!started) {
            throw new LifecycleException("尚未start......");
        }
        support.fireLifecycleEvent(Lifecycle.BEFORE_STOP_EVENT, null);

        // 调用Loader的start
        ((Lifecycle) loader).stop();

        // 调用Mapper的start
        for (Mapper mapper : mappers) {
            ((Lifecycle) mapper).stop();
        }

        if(logger instanceof Lifecycle){
            // 调用Logger
            ((Lifecycle) logger).stop();
        }

        // 调用Container
        for (Container container : childContainers.values()) {
            ((Lifecycle) container).stop();
        }

        support.fireLifecycleEvent(Lifecycle.STOP_EVENT, null);
        support.fireLifecycleEvent(Lifecycle.AFTER_STOP_EVENT, null);

        started = false;
    }

}
