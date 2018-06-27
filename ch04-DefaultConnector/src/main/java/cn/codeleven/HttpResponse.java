package cn.codeleven;

import cn.codeleven.response.PrintWriter;
import cn.codeleven.response.ResponseStream;

import javax.servlet.ServletOutputStream;
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
public class HttpResponse implements HttpServletResponse {

    private static final int BUFFER_SIZE = 1024;
    private OutputStream originalOutputStream;
    private HttpRequest request;
    private String characterEncoding;
    private String contentType;
    private ResponseStream responseStream;
    private int contentLength;
    private Map<String, String> headers = new HashMap<>();
    private Locale locale = Locale.getDefault();
    private List<Cookie> cookieList = new ArrayList<>();
    private int status = SC_OK;
    // 时间格式化
    private SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
    private String message;
    private boolean commited;
    private PrintWriter writer;

    public HttpResponse(OutputStream os) {
        this.originalOutputStream = os;
        // 封装后的ServletOutputStream，其实内部用的是原生的SocketOutputStream
        responseStream = new ResponseStream(this);

    }

    /**
     * 这段直接复制的，懒得一个个敲了
     *
     * @return
     */
    protected String getMessage(){
        switch (status) {
            case SC_OK:
                return ("OK");
            case SC_ACCEPTED:
                return ("Accepted");
            case SC_BAD_GATEWAY:
                return ("Bad Gateway");
            case SC_BAD_REQUEST:
                return ("Bad Request");
            case SC_CONFLICT:
                return ("Conflict");
            case SC_CONTINUE:
                return ("Continue");
            case SC_CREATED:
                return ("Created");
            case SC_EXPECTATION_FAILED:
                return ("Expectation Failed");
            case SC_FORBIDDEN:
                return ("Forbidden");
            case SC_GATEWAY_TIMEOUT:
                return ("Gateway Timeout");
            case SC_GONE:
                return ("Gone");
            case SC_HTTP_VERSION_NOT_SUPPORTED:
                return ("HTTP Version Not Supported");
            case SC_INTERNAL_SERVER_ERROR:
                return ("Internal Server Error");
            case SC_LENGTH_REQUIRED:
                return ("Length Required");
            case SC_METHOD_NOT_ALLOWED:
                return ("Method Not Allowed");
            case SC_MOVED_PERMANENTLY:
                return ("Moved Permanently");
            case SC_MOVED_TEMPORARILY:
                return ("Moved Temporarily");
            case SC_MULTIPLE_CHOICES:
                return ("Multiple Choices");
            case SC_NO_CONTENT:
                return ("No Content");
            case SC_NON_AUTHORITATIVE_INFORMATION:
                return ("Non-Authoritative Information");
            case SC_NOT_ACCEPTABLE:
                return ("Not Acceptable");
            case SC_NOT_FOUND:
                return ("Not Found");
            case SC_NOT_IMPLEMENTED:
                return ("Not Implemented");
            case SC_NOT_MODIFIED:
                return ("Not Modified");
            case SC_PARTIAL_CONTENT:
                return ("Partial Content");
            case SC_PAYMENT_REQUIRED:
                return ("Payment Required");
            case SC_PRECONDITION_FAILED:
                return ("Precondition Failed");
            case SC_PROXY_AUTHENTICATION_REQUIRED:
                return ("Proxy Authentication Required");
            case SC_REQUEST_ENTITY_TOO_LARGE:
                return ("Request Entity Too Large");
            case SC_REQUEST_TIMEOUT:
                return ("Request Timeout");
            case SC_REQUEST_URI_TOO_LONG:
                return ("Request URI Too Long");
            case SC_REQUESTED_RANGE_NOT_SATISFIABLE:
                return ("Requested Range Not Satisfiable");
            case SC_RESET_CONTENT:
                return ("Reset Content");
            case SC_SEE_OTHER:
                return ("See Other");
            case SC_SERVICE_UNAVAILABLE:
                return ("Service Unavailable");
            case SC_SWITCHING_PROTOCOLS:
                return ("Switching Protocols");
            case SC_UNAUTHORIZED:
                return ("Unauthorized");
            case SC_UNSUPPORTED_MEDIA_TYPE:
                return ("Unsupported Media Type");
            case SC_USE_PROXY:
                return ("Use Proxy");
            case 207:       // WebDAV
                return ("Multi-Status");
            case 422:       // WebDAV
                return ("Unprocessable Entity");
            case 423:       // WebDAV
                return ("Locked");
            case 507:       // WebDAV
                return ("Insufficient Storage");
            default:
                return ("HTTP Response Status " + status);
        }
    }

    public void sendHeaders() {
        if(this.commited){
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

        this.commited = true;
    }

    public void finishResponse(){
        writer.flush();
        writer.close();
    }

    public OutputStream getOriginalOutputStream() {
        return originalOutputStream;
    }

    public void sendStaticResource() {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            String header = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: 14\r\n" +
                    "\r\n";

            File file = new File(WEB_ROOT + File.separator + request.getPath());
            if (file.exists()) {
                originalOutputStream.write(header.getBytes());
                fis = new FileInputStream(file);
                int len = fis.read(bytes);
                while (len != -1) {
                    originalOutputStream.write(bytes, 0, len);
                    len = fis.read(bytes);
                }
            } else {
                System.out.println("-------没有找到文件--------");
                String message = "HTTP/1.1 404 File Not Found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 23\r\n" +
                        "\r\n" +
                        "<h1>File Not Found</h1>\r\n";
                originalOutputStream.write(message.getBytes());
                originalOutputStream.flush();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public String getCharacterEncoding() {
        return this.characterEncoding;
    }

    public void setCharacterEncoding(String s) {
        this.characterEncoding = s;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String s) {
        this.contentType = s;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        return this.responseStream;
    }

    public PrintWriter getWriter() throws IOException {
        // 将二进制流按 按指定编码 进行编码
        // 因为编码设置后就不可改变了，所以这里重新生成一个
        OutputStreamWriter osw = new OutputStreamWriter(responseStream, Charset.forName(getCharacterEncoding()));
        this.writer = new PrintWriter(osw);
        sendHeaders();
        // 重新替换该值
        return this.writer;
    }

    public void setContentLength(int i) {
        this.contentLength = i;
    }

    public int getBufferSize() {
        return 0;
    }

    public void setBufferSize(int i) {

    }

    public void flushBuffer() throws IOException {

    }

    public void resetBuffer() {

    }

    public boolean isCommitted() {
        return this.commited;
    }

    public void reset() {

    }

    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;

        StringBuilder result = new StringBuilder();

        result.append(locale.getLanguage()).append('-').append(locale.getCountry());
        addHeader("Content-Language", result.toString());
    }

    public void addCookie(Cookie cookie) {
        this.cookieList.add(cookie);
    }

    public boolean containsHeader(String s) {
        return headers.containsKey(s);
    }

    public String encodeURL(String s) {
        return null;
    }

    public String encodeRedirectURL(String s) {
        return null;
    }

    public String encodeUrl(String s) {
        return null;
    }

    public String encodeRedirectUrl(String s) {
        return null;
    }

    public void sendError(int i, String s) throws IOException {

    }

    public void sendError(int i) throws IOException {

    }

    public void sendRedirect(String s) throws IOException {

    }

    public void setDateHeader(String s, long l) {
        addDateHeader(s, l);
    }

    public void addDateHeader(String s, long l) {
        addHeader(s, sdf.format(new Date(l)));
    }

    public void setHeader(String s, String s1) {
        headers.put(s, s1);
    }

    public void addHeader(String s, String s1) {
        setHeader(s, s1);
    }

    public void setIntHeader(String s, int i) {
        setHeader(s, i + "");
    }

    public void addIntHeader(String s, int i) {
        setIntHeader(s, i);
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public void setStatus(int i, String s) {
        this.status = i;
        this.message = s;
    }
}
