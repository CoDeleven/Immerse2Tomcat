package cn.codeleven.response;

import cn.codeleven.SimpleRequest;
import cn.codeleven.SimpleResponse;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/19
 */
public class ResponseFacade implements ServletResponse {
    private SimpleResponse response;

    public ResponseFacade(SimpleResponse response) {
        this.response = response;
    }

    public void setRequest(SimpleRequest request) {
        response.setRequest(request);
    }


    public String getCharacterEncoding() {
        return response.getCharacterEncoding();
    }

    public String getContentType() {
        return response.getContentType();
    }

    public ServletOutputStream getOutputStream() throws IOException {
        return response.getOutputStream();
    }

    public PrintWriter getWriter() throws IOException {
        return response.getWriter();
    }

    public void setCharacterEncoding(String s) {
        response.setCharacterEncoding(s);
    }

    public void setContentLength(int i) {
        response.setContentLength(i);
    }

    public void setContentType(String s) {
        response.setContentType(s);
    }

    public void setBufferSize(int i) {
        response.setBufferSize(i);
    }

    public int getBufferSize() {
        return response.getBufferSize();
    }

    public void flushBuffer() throws IOException {
        response.flushBuffer();
    }

    public void resetBuffer() {
        response.resetBuffer();
    }

    public boolean isCommitted() {
        return response.isCommitted();
    }

    public void reset() {
        response.reset();
    }

    public void setLocale(Locale locale) {
        response.setLocale(locale);
    }

    public Locale getLocale() {
        return response.getLocale();
    }
}
