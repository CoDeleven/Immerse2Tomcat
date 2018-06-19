package cn.codeleven;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import static cn.codeleven.Server.WEB_ROOT;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/19
 */
public class ServletProcessor {
    public void process(Request request, Response response) throws ClassNotFoundException, IllegalAccessException, InstantiationException, ServletException, IOException {
        String repositoryUrl = null;
        String uri = request.getUri();
        repositoryUrl = new URL("file", null, WEB_ROOT + File.separator).toString();
        ClassLoader loader = new URLClassLoader(new URL[]{new URL(repositoryUrl)});
        // 这里要给全限定名，否则无法加载到
        Class servletClass = loader.loadClass(uri.substring(uri.lastIndexOf("/") + 1));
        Servlet servlet = (Servlet)servletClass.newInstance();
        servlet.service(new RequestFacade(request), new ResponseFacade(response));
    }
}
