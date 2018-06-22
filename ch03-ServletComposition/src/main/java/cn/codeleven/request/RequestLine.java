package cn.codeleven.request;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/20
 */
public class RequestLine {
    private String method;
    private String path;
    private String protocol;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
