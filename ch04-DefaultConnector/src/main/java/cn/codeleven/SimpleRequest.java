package cn.codeleven;


import cn.codeleven.request.ParameterMap;
import cn.codeleven.request.RequestLine;
import cn.codeleven.request.RequestStream;
import cn.codeleven.request.RequestUtil;
import org.apache.catalina.*;
import org.apache.catalina.connector.HttpRequestBase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.security.Principal;
import java.util.*;

/**
 * 一个简单的请求类，原来版本的无法兼容Apache的Container接口
 * 而且后续都不会涉及Request的讲解，所以在第四章就进行完善
 * 后续仍然能运行
 *
 * Author: CoDeleven
 * Date: 2018/6/18
 */
public class SimpleRequest implements HttpRequest, HttpServletRequest {
    private String authType;
    private List<Cookie> cookies;
    private String requestedSessionId;
    private String protocol;

    @Override
    public String getAuthType() {
        return this.authType;
    }

    @Override
    public Cookie[] getCookies() {
        return cookies.toArray(new Cookie[]{});
    }

    @Override
    public long getDateHeader(String s) {
        return 0;
    }

    @Override
    public String getHeader(String s) {
        return null;
    }

    @Override
    public Enumeration getHeaders(String s) {
        return null;
    }

    @Override
    public Enumeration getHeaderNames() {
        return null;
    }

    @Override
    public int getIntHeader(String s) {
        return 0;
    }

    @Override
    public String getMethod() {
        return null;
    }

    @Override
    public String getPathInfo() {
        return null;
    }

    @Override
    public String getPathTranslated() {
        return null;
    }

    @Override
    public String getContextPath() {
        return null;
    }

    @Override
    public String getQueryString() {
        return null;
    }

    @Override
    public String getRemoteUser() {
        return null;
    }

    @Override
    public boolean isUserInRole(String s) {
        return false;
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public String getRequestedSessionId() {
        return this.requestedSessionId;
    }
    private String requestURI;
    @Override
    public String getRequestURI() {
        return requestURI;
    }
    private StringBuffer requestURL;
    @Override
    public StringBuffer getRequestURL() {
        return requestURL;
    }

    @Override
    public String getServletPath() {
        return null;
    }

    @Override
    public HttpSession getSession(boolean b) {
        return null;
    }

    @Override
    public HttpSession getSession() {
        return null;
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return false;
    }
    private boolean requestedSessionIdFromCookie;
    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return this.requestedSessionIdFromCookie;
    }
    private boolean requestedSessionIdFromURL;
    @Override
    public boolean isRequestedSessionIdFromURL() {
        return this.requestedSessionIdFromURL;
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return this.requestedSessionIdFromURL;
    }

    @Override
    public Object getAttribute(String s) {
        return null;
    }

    @Override
    public Enumeration getAttributeNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

    }
    private int contentLength;
    @Override
    public int getContentLength() {
        return this.contentLength;
    }
    private String contentType;
    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public String getParameter(String s) {
        String[] value = parameterMap.get(s);
        return  value != null ? value[0] : null;
    }

    @Override
    public Enumeration getParameterNames() {
        return null;
    }

    @Override
    public String[] getParameterValues(String s) {
        return parameterMap.get(s);
    }

    @Override
    public Map getParameterMap() {
        return this.parameterMap;
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    @Override
    public String getScheme() {
        return this.scheme;
    }

    @Override
    public String getServerName() {
        return this.serverName;
    }

    @Override
    public int getServerPort() {
        return 0;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return null;
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @Override
    public String getRemoteHost() {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {

    }

    @Override
    public void removeAttribute(String s) {

    }

    @Override
    public Locale getLocale() {
        return this.locale;
    }
    private Locale locale;
    @Override
    public Enumeration getLocales() {
        return null;
    }
    private boolean secure;
    @Override
    public boolean isSecure() {
        return secure;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    @Override
    public String getRealPath(String s) {
        return null;
    }

    @Override
    public int getRemotePort() {
        return 0;
    }

    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public String getLocalAddr() {
        return null;
    }

    @Override
    public int getLocalPort() {
        return 0;
    }

    @Override
    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String[]> parameterMap = new HashMap<>();
    @Override
    public void addHeader(String name, String value) {
        this.headers.put(name, value);
    }

    @Override
    public void addLocale(Locale locale) {

    }

    @Override
    public void addParameter(String name, String[] values) {

    }

    @Override
    public void clearCookies() {
        cookies.clear();
    }

    @Override
    public void clearHeaders() {
        headers.clear();
    }

    @Override
    public void clearLocales() {

    }

    @Override
    public void clearParameters() {
        parameterMap.clear();
    }

    @Override
    public void setAuthType(String type) {
        this.authType = type;
    }

    @Override
    public void setContextPath(String path) {

    }
    private String method;
    @Override
    public void setMethod(String method) {
        this.method = method;
    }
    private String queryString;
    @Override
    public void setQueryString(String query) {
        this.queryString = query;
    }

    @Override
    public void setPathInfo(String path) {

    }

    @Override
    public void setRequestedSessionCookie(boolean flag) {
        this.requestedSessionIdFromCookie = flag;
    }

    @Override
    public void setRequestedSessionId(String id) {
        this.requestedSessionId = id;
    }

    @Override
    public void setRequestedSessionURL(boolean flag) {
        this.requestedSessionIdFromURL = true;
    }

    @Override
    public void setRequestURI(String uri) {
        this.requestURI = uri;
    }

    @Override
    public void setDecodedRequestURI(String uri) {

    }

    @Override
    public String getDecodedRequestURI() {
        return null;
    }

    @Override
    public void setServletPath(String path) {

    }

    @Override
    public void setUserPrincipal(Principal principal) {

    }

    @Override
    public String getAuthorization() {
        return null;
    }

    @Override
    public void setAuthorization(String authorization) {

    }

    @Override
    public Connector getConnector() {
        return null;
    }

    @Override
    public void setConnector(Connector connector) {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void setContext(Context context) {

    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public ServletRequest getRequest() {
        return null;
    }

    @Override
    public Response getResponse() {
        return null;
    }

    @Override
    public void setResponse(Response response) {

    }

    @Override
    public Socket getSocket() {
        return null;
    }

    @Override
    public void setSocket(Socket socket) {

    }
    private InputStream is;
    @Override
    public InputStream getStream() {
        return this.is;
    }

    @Override
    public void setStream(InputStream stream) {
        this.is = stream;
    }

    @Override
    public Wrapper getWrapper() {
        return null;
    }

    @Override
    public void setWrapper(Wrapper wrapper) {

    }
    private SocketInputStream inputStream;
    @Override
    public ServletInputStream createInputStream() throws IOException {
        RequestStream requestStream = new RequestStream(this);
        return requestStream;
    }

    @Override
    public void finishRequest() throws IOException {

    }

    @Override
    public Object getNote(String name) {
        return null;
    }

    @Override
    public Iterator getNoteNames() {
        return null;
    }

    @Override
    public void recycle() {

    }

    @Override
    public void removeNote(String name) {

    }

    @Override
    public void setContentLength(int length) {

    }

    @Override
    public void setContentType(String type) {

    }

    @Override
    public void setNote(String name, Object value) {

    }

    @Override
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public void setRemoteAddr(String remote) {

    }
    private String scheme;
    @Override
    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    @Override
    public void setSecure(boolean secure) {
        this.secure = secure;
    }
    private String serverName;
    @Override
    public void setServerName(String name) {
        this.serverName = name;
    }

    @Override
    public void setServerPort(int port) {

    }
}
