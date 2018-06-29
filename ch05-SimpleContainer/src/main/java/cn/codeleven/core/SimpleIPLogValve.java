package cn.codeleven.core;

import org.apache.catalina.*;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/29
 */
public class SimpleIPLogValve implements Valve,Contained {
    private Container container;

    @Override
    public Container getContainer() {
        return container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void invoke(Request request, Response response, ValveContext context) throws IOException, ServletException {
        System.out.println("--------SimpleIPLog----------");
        System.out.println(request.getRequest().getRemoteAddr());
        context.invokeNext(request, response);
    }
}
