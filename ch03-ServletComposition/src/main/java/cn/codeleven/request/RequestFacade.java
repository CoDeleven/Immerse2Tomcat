package cn.codeleven.request;

import cn.codeleven.Request;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * 为什么有了Request还要有RequestFacade呢
 * 因为Request的parse方法仍然要被其他内部类所使用，为了不让外部开发人员（阅读源码后，通过强转）随意的使用parse方法
 * 所以将Request作为私有属性，放到RequestFacade里，就能保证传递给开发人员的只有RequestFacade而不会让开发人员使用到不该使用的方法
 * Author: CoDeleven
 * Date: 2018/6/19
 */
public class RequestFacade implements ServletRequest {
    private Request request;

    public RequestFacade(Request request) {
        this.request = request;
    }

    public Object getAttribute(String s) {
        return request.getAttribute(s);
    }

    public Enumeration getAttributeNames() {
        return request.getAttributeNames();
    }

    public String getCharacterEncoding() {
        return request.getCharacterEncoding();
    }

    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {
        request.setCharacterEncoding(s);
    }

    public int getContentLength() {
        return request.getContentLength();
    }

    public String getContentType() {
        return request.getContentType();
    }

    public ServletInputStream getInputStream() throws IOException {
        return request.getInputStream();
    }

    public String getParameter(String s) {
        return request.getParameter(s);
    }

    public Enumeration getParameterNames() {
        return request.getParameterNames();
    }

    public String[] getParameterValues(String s) {
        return request.getParameterValues(s);
    }

    public Map getParameterMap() {
        return request.getParameterMap();
    }

    public String getProtocol() {
        return request.getProtocol();
    }

    public String getScheme() {
        return request.getScheme();
    }

    public String getServerName() {
        return request.getServerName();
    }

    public int getServerPort() {
        return request.getServerPort();
    }

    public BufferedReader getReader() throws IOException {
        return request.getReader();
    }

    public String getRemoteAddr() {
        return request.getRemoteAddr();
    }

    public String getRemoteHost() {
        return request.getRemoteHost();
    }

    public void setAttribute(String s, Object o) {
        request.setAttribute(s, o);
    }

    public void removeAttribute(String s) {
        request.removeAttribute(s);
    }

    public Locale getLocale() {
        return request.getLocale();
    }

    public Enumeration getLocales() {
        return request.getLocales();
    }

    public boolean isSecure() {
        return request.isSecure();
    }

    public RequestDispatcher getRequestDispatcher(String s) {
        return request.getRequestDispatcher(s);
    }

    @Deprecated
    public String getRealPath(String s) {
        return request.getRealPath(s);
    }

    public int getRemotePort() {
        return request.getRemotePort();
    }

    public String getLocalName() {
        return request.getLocalName();
    }

    public String getLocalAddr() {
        return request.getLocalAddr();
    }

    public int getLocalPort() {
        return request.getLocalPort();
    }
}
