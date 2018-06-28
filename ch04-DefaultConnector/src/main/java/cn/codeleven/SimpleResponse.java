package cn.codeleven;

import cn.codeleven.response.PrintWriter;
import cn.codeleven.response.ResponseStream;
import org.apache.catalina.Connector;
import org.apache.catalina.Context;
import org.apache.catalina.HttpResponse;
import org.apache.catalina.Request;
import org.apache.catalina.connector.HttpResponseBase;
import org.apache.catalina.connector.ResponseBase;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

import static cn.codeleven.SimpleContainer.WEB_ROOT;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/18
 */
public class SimpleResponse implements HttpResponse, HttpServletResponse {
    private PrintWriter writer;

    @Override
    public void addCookie(Cookie cookie) {

    }

    @Override
    public boolean containsHeader(String s) {
        return false;
    }

    @Override
    public String encodeURL(String s) {
        return null;
    }

    @Override
    public String encodeRedirectURL(String s) {
        return null;
    }

    @Override
    public String encodeUrl(String s) {
        return null;
    }

    @Override
    public String encodeRedirectUrl(String s) {
        return null;
    }

    @Override
    public void sendError(int i, String s) throws IOException {

    }

    @Override
    public void sendError(int i) throws IOException {

    }

    @Override
    public void sendRedirect(String s) throws IOException {

    }

    @Override
    public void setDateHeader(String s, long l) {

    }

    @Override
    public void addDateHeader(String s, long l) {

    }

    @Override
    public void setHeader(String s, String s1) {

    }

    @Override
    public void addHeader(String s, String s1) {

    }

    @Override
    public void setIntHeader(String s, int i) {

    }

    @Override
    public void addIntHeader(String s, int i) {

    }

    @Override
    public void setStatus(int i) {

    }

    @Override
    public void setStatus(int i, String s) {

    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return this.responseStream;
    }

    @Override
    public java.io.PrintWriter getWriter() throws IOException {
        this.writer = new PrintWriter(new OutputStreamWriter(responseStream));
        return writer;
    }

    @Override
    public void setCharacterEncoding(String s) {

    }

    @Override
    public void setContentLength(int i) {

    }

    @Override
    public void setContentType(String s) {

    }

    @Override
    public void setBufferSize(int i) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale locale) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Cookie[] getCookies() {
        return new Cookie[0];
    }

    @Override
    public String getHeader(String name) {
        return null;
    }

    @Override
    public String[] getHeaderNames() {
        return new String[0];
    }

    @Override
    public String[] getHeaderValues(String name) {
        return new String[0];
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public int getStatus() {
        return 0;
    }

    @Override
    public void reset(int status, String message) {

    }

    @Override
    public Connector getConnector() {
        return null;
    }

    @Override
    public void setConnector(Connector connector) {

    }

    @Override
    public int getContentCount() {
        return 0;
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void setContext(Context context) {

    }

    @Override
    public void setAppCommitted(boolean appCommitted) {

    }

    @Override
    public boolean isAppCommitted() {
        return false;
    }

    @Override
    public boolean getIncluded() {
        return false;
    }

    @Override
    public void setIncluded(boolean included) {

    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public Request getRequest() {
        return null;
    }

    @Override
    public void setRequest(Request request) {

    }

    @Override
    public ServletResponse getResponse() {
        return null;
    }

    @Override
    public OutputStream getStream() {
        return this.outputStream;
    }

    @Override
    public void setStream(OutputStream stream) {
        this.outputStream = stream;
    }

    @Override
    public void setSuspended(boolean suspended) {

    }

    @Override
    public boolean isSuspended() {
        return false;
    }

    @Override
    public void setError() {

    }

    @Override
    public boolean isError() {
        return false;
    }

    private ResponseStream responseStream;

    private OutputStream outputStream;

    @Override
    public ServletOutputStream createOutputStream() throws IOException {
        this.responseStream = new ResponseStream(this);
        return this.responseStream;
    }

    @Override
    public void finishResponse() throws IOException {

    }

    @Override
    public int getContentLength() {
        return 0;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public java.io.PrintWriter getReporter() {
        return null;
    }

    @Override
    public void recycle() {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public void sendAcknowledgement() throws IOException {

    }

    public void sendHeaders() {
        // todo 在这里输出响应头等等信息

       /* if(this.commited){
            return;
        }
        // 输出响应行
        this.writer.print(request.getProtocol());
        this.writer.print(' ');
        this.writer.print(status);
        this.writer.print(' ');
        this.writer.print(getMessage());
        this.writer.print("\r\n");

        // 输出响应头
        headers.forEach((accept1, accept2) -> {
            this.writer.print(accept1);
            this.writer.print(":");
            this.writer.print(accept2);
            this.writer.print("\r\n");
        });


        // 输出cookie
        cookieList.forEach((cookie) -> {
            this.writer.print("set-cookie:");
            this.writer.print(cookie.getName());
            this.writer.print('=');
            this.writer.print(cookie.getValue());

            if(cookie.getDomain() != null){
                this.writer.print(';');
                this.writer.print("Domain");
                this.writer.print('=');
                this.writer.print(cookie.getDomain());
            }
            if(cookie.getSecure()){
                this.writer.print(';');
                this.writer.print("Secure");
            }
        });

        this.writer.print("\r\n");

        this.commited = true;*/
    }
}
