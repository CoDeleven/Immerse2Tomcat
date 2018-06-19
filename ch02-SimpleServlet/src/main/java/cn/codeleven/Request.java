package cn.codeleven;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import java.io.*;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/18
 */
public class Request implements ServletRequest {
    private String uri;
    private InputStream is;
    public Request(InputStream inputStream) {
        this.is = inputStream;
    }

    public void parse(){
        byte[] bytes = new byte[2048];
        int len = -1;
        try {
            len = is.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder request = new StringBuilder();
        request.append(new String(bytes, 0, len));
        System.out.println(request.toString());
        this.uri = parseUri(request.toString());
    }

    private String parseUri(String request){
        int firstIndex = request.indexOf('/');
        int endIndex =request.indexOf(" ", firstIndex);
        return request.substring(firstIndex, endIndex);
    }

    public String getUri() {
        return this.uri;
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
        return 0;
    }

    public String getContentType() {
        return null;
    }

    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    public String getParameter(String s) {
        return null;
    }

    public Enumeration getParameterNames() {
        return null;
    }

    public String[] getParameterValues(String s) {
        return new String[0];
    }

    public Map getParameterMap() {
        return null;
    }

    public String getProtocol() {
        return null;
    }

    public String getScheme() {
        return null;
    }

    public String getServerName() {
        return null;
    }

    public int getServerPort() {
        return 0;
    }

    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(is));
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
}
