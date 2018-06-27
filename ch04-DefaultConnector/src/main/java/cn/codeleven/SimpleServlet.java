package cn.codeleven;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

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

    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        servletResponse.setCharacterEncoding("utf-8");
        servletResponse.setContentType("text/plain");
        PrintWriter printWriter = servletResponse.getWriter();

        printWriter.println("scheme:" + servletRequest.getScheme());
        printWriter.println("protocol:" + servletRequest.getProtocol());
        printWriter.println("------------End----------------");

        printWriter.flush();
    }

    public String getServletInfo() {
        return null;
    }

    public void destroy() {

    }
}
