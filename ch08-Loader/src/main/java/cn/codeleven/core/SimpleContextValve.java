package cn.codeleven.core;

import org.apache.catalina.*;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/30
 */
public class SimpleContextValve implements Valve, Contained {
    private Container container;

    @Override
    public String getInfo() {
        return null;
    }

    public SimpleContextValve(Container container) {
        this.container = container;
    }

    @Override
    public void invoke(Request request, Response response, ValveContext context) throws IOException, ServletException {
        // 对请求进行转发
        // 这里运行时可能会再报一次错，主要是因为chrome会发起一次查找favicon.icon的请求，因为没处理就找不到对应的Mapper，所以就没有Container了
        Container container = getContainer().map(request, false);
        container.invoke(request, response);
    }

    @Override
    public Container getContainer() {
        return this.container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }
}
