package cn.codeleven.core;

import org.apache.catalina.Container;
import org.apache.catalina.Mapper;
import org.apache.catalina.Request;

import javax.servlet.http.HttpServletRequest;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/30
 */
public class SimpleContextMapper implements Mapper {
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
}
