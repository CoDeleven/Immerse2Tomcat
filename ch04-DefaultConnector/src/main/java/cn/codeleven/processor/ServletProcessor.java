package cn.codeleven.processor;

import cn.codeleven.HttpRequest;
import cn.codeleven.HttpResponse;
import cn.codeleven.request.RequestFacade;
import cn.codeleven.response.ResponseFacade;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import static cn.codeleven.SimpleContainer.WEB_ROOT;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/19
 */
public class ServletProcessor {
    public void process(HttpRequest request, HttpResponse response) throws ClassNotFoundException, IllegalAccessException, InstantiationException, ServletException, IOException {
        String repositoryUrl = null;
        String uri = request.getPath();
        repositoryUrl = new URL("file", null, WEB_ROOT + File.separator).toString();
        ClassLoader loader = new URLClassLoader(new URL[]{new URL(repositoryUrl)});

        // 这里要给全限定名，否则无法加载到
        Class servletClass = loader.loadClass("cn.codeleven." + uri.substring(uri.lastIndexOf("/") + 1));
        Servlet servlet = (Servlet)servletClass.newInstance();
        servlet.service(new RequestFacade(request), new ResponseFacade(response));

        response.finishResponse();
    }
}
