package cn.codeleven;

import cn.codeleven.response.ResponseStream;
import cn.codeleven.response.ResponseWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/18
 */
public class Response implements HttpServletResponse {

    private static final int BUFFER_SIZE = 1024;
    private OutputStream originalOutputStream;
    private HttpRequest request;
    private String characterEncoding;
    private String contentType;
    private ResponseStream responseStream;
    private int contentLength;
    private Map<String, String> headers;
    private Locale locale = Locale.getDefault();
    private List<Cookie> cookieList = new ArrayList<>();
    private int status;

    protected void sendHeaders(){
        PrintWriter writer = new PrintWriter(originalOutputStream);

        writer.print(request.getProtocol());
        writer.print(' ');
        writer.print(status);

        // todo 根据status查询message

        writer.print("\r\n");


    }


    // 时间格式化
    private SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);

    public Response(OutputStream os) {
        this.originalOutputStream = os;
        // 封装后的ServletOutputStream，其实内部用的是原生的SocketOutputStream
        responseStream = new ResponseStream(this);

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

            File file = new File(Application.WEB_ROOT + File.separator + request.getPath());
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
        // 重新替换该值
        return new ResponseWriter(osw);
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
        return false;
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

    }

    public void setStatus(int i, String s) {

    }
}
