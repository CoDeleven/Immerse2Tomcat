package cn.codeleven;


import cn.codeleven.request.ParameterMap;
import cn.codeleven.request.RequestLine;
import cn.codeleven.request.RequestStream;
import cn.codeleven.request.RequestUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/18
 */
public class HttpRequest implements HttpServletRequest {
    private String path;
    private SocketInputStream socketInputStream;
    private RequestLine requestLine;
    private String queryString;
    private String jsessionid;
    // 判断jsessionid是在cookie里还是url里
    private boolean requestedSessionIdFromUrl;
    private String method;
    private String scheme;
    private String protocol;
    private Map<String, String> headers = new HashMap<String, String>();
    private String contentType;
    private List<Cookie> cookies = new ArrayList<Cookie>();
    private boolean parsedParameter;
    private Map<String, String> parameterMap = new ParameterMap();
    private boolean requestedSessionIdFromCookie;
    private int contentLength;


    private RequestStream requestStream;
    public HttpRequest(SocketInputStream socketInputStream) {
        this.requestStream = new RequestStream(this);
        this.socketInputStream = socketInputStream;
    }

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getJsessionid() {
        return jsessionid;
    }

    public void setJsessionid(String jsessionid) {
        this.jsessionid = jsessionid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public void setRequestLine(RequestLine requestLine) {
        this.requestLine = requestLine;
    }

    public String getAuthType() {
        return null;
    }

    public Cookie[] getCookies() {
        return new Cookie[0];
    }

    public long getDateHeader(String s) {
        return 0;
    }

    public String getHeader(String s) {
        return null;
    }

    public Enumeration getHeaders(String s) {
        return null;
    }

    public Enumeration getHeaderNames() {
        return null;
    }

    public int getIntHeader(String s) {
        return 0;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPathInfo() {
        return null;
    }

    public String getPathTranslated() {
        return null;
    }

    public String getContextPath() {
        return null;
    }

    public String getQueryString() {
        return null;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getRemoteUser() {
        return null;
    }

    public boolean isUserInRole(String s) {
        return false;
    }

    public Principal getUserPrincipal() {
        return null;
    }

    public String getRequestedSessionId() {
        return this.jsessionid;
    }

    public String getRequestURI() {
        return null;
    }

    public StringBuffer getRequestURL() {
        return null;
    }

    public String getServletPath() {
        return null;
    }

    public HttpSession getSession(boolean b) {
        return null;
    }

    public HttpSession getSession() {
        return null;
    }

    public boolean isRequestedSessionIdValid() {
        return false;
    }

    public boolean isRequestedSessionIdFromCookie() {
        return this.requestedSessionIdFromCookie;
    }

    public void setRequestedSessionIdFromCookie(boolean requestedSessionIdFromCookie) {
        this.requestedSessionIdFromCookie = requestedSessionIdFromCookie;
    }

    public boolean isRequestedSessionIdFromURL() {
        return this.requestedSessionIdFromUrl;
    }

    @Deprecated
    public boolean isRequestedSessionIdFromUrl() {
        return this.requestedSessionIdFromUrl;
    }

    public void setRequestedSessionIdFromUrl(boolean requestedSessionIdFromUrl) {
        this.requestedSessionIdFromUrl = requestedSessionIdFromUrl;
    }

    public Object getAttribute(String s) {
        return null;
    }

    public Enumeration getAttributeNames() {
        return null;
    }

    public String getCharacterEncoding() {
        return null;
    }

    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

    }

    public int getContentLength() {
        return this.contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String value) {
        this.contentType = value;
    }

    public ServletInputStream getInputStream() {
        return this.requestStream;
    }

    public String getParameter(String s) {
        if (!parsedParameter) {
            parseParameter();
        }
        return this.parameterMap.get(s);
    }

    /**
     * 解析请求参数
     */
    private void parseParameter() {
        String encoding = getCharacterEncoding();

        if (encoding == null) {
            encoding = "ISO-8859-1";
        }
        RequestUtil.parseRequest(this.parameterMap, queryString);

        ((ParameterMap) this.parameterMap).setLocked(true);
        // 已经解析过请求参数
        this.parsedParameter = true;
    }

    public Enumeration getParameterNames() {
        return null;
    }

    public String[] getParameterValues(String s) {
        return new String[0];
    }

    public Map getParameterMap() {
        return this.parameterMap;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getServerName() {
        return null;
    }

    public int getServerPort() {
        return 0;
    }

    public BufferedReader getReader() throws IOException {
        return null;
    }

    public String getRemoteAddr() {
        return null;
    }

    public String getRemoteHost() {
        return null;
    }

    public void setAttribute(String s, Object o) {

    }

    public void removeAttribute(String s) {

    }

    public Locale getLocale() {
        return null;
    }

    public Enumeration getLocales() {
        return null;
    }

    public boolean isSecure() {
        return false;
    }

    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    @Deprecated
    public String getRealPath(String s) {
        return null;
    }

    public int getRemotePort() {
        return 0;
    }

    public String getLocalName() {
        return null;
    }

    public String getLocalAddr() {
        return null;
    }

    public int getLocalPort() {
        return 0;
    }

    public InputStream getOriginalStream() {
        return this.socketInputStream;
    }
}
