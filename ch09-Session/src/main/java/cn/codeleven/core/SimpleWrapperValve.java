package cn.codeleven.core;

import org.apache.catalina.*;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/29
 */
public class SimpleWrapperValve implements Valve,Contained {
    public SimpleWrapperValve(Container container) {
        this.container = container;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void invoke(Request request, Response response, ValveContext context) throws IOException, ServletException {
        ServletRequest servletRequest = request.getRequest();
        ServletResponse servletResponse = response.getResponse();
        Servlet servlet = ((Wrapper)getContainer()).allocate();

        request.setContext((Context)container.getParent());
        servlet.service(servletRequest, servletResponse);

    }

    @Override
    public Container getContainer() {
        return this.container;
    }
    private Container container;
    @Override
    public void setContainer(Container container) {
        this.container = container;
    }
}
