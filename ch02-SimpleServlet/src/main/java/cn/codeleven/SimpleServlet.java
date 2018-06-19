package cn.codeleven;

import javax.servlet.*;
import java.io.IOException;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/19
 */
public class SimpleServlet implements Servlet {
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("初始化...");
    }

    public ServletConfig getServletConfig() {
        return null;
    }

    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        // TODO 处理
    }

    public String getServletInfo() {
        return null;
    }

    public void destroy() {

    }
}
