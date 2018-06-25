package cn.codeleven;

import cn.codeleven.request.RequestHeader;
import cn.codeleven.request.RequestLine;
import jdk.internal.util.xml.impl.Input;

import java.io.IOException;
import java.io.InputStream;

/**
 * 用于解析请求行、请求头两个内容的封装Stream
 *
 * 不是很理解源码为什么只对char数组进行操作。
 * 个人猜测是发送的HTTP请求不合规范，所以一个个字节查看
 *
 * Author: CoDeleven
 * Date: 2018/6/20
 */
public class SocketInputStream extends InputStream {
    private InputStream is;
    public int read() throws IOException {
        return is.read();
    }

    public SocketInputStream(InputStream is) {
        this.is = is;
    }

    public void readRequestLine(RequestLine requestLine) {
        try {
            // 读取请求行
            requestLine.setMethod(readUntilSpace());
            // 读取请求路径
            requestLine.setPath(readUntilSpace());
            // 读取版本号
            requestLine.setProtocol(readUntilCRLF());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readHeader(RequestHeader header) {
        try {
            String keyValString = readUntilCRLF();
            int colonIndex = keyValString.indexOf(":");
            if (colonIndex != -1) {
                header.setKey(keyValString.substring(0, colonIndex));
                header.setValue(keyValString.substring(colonIndex + 1, keyValString.length()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String readUntilSpace() throws IOException {
        StringBuilder sb = new StringBuilder();
        int ch;
        while ((ch = is.read()) != -1) {
            if (ch == 32) {
                // 已经读取过空格，所以不用担心
                break;
            }
            sb.append((char) ch);
        }
        return sb.toString();
    }

    /**
     * 读取内容，直到遇见CRLF
     *
     * @return 返回的字符串不会包含CRLF
     * @throws IOException
     */
    private String readUntilCRLF() throws IOException {
        StringBuilder sb = new StringBuilder();
        int ch;
        while ((ch = is.read()) != -1) {
            // 如果遇到return符号，则设置ret为true
            if (ch == 13) {
                // 直接读取下一个
                ch = is.read();
                // 如果遇到newline符号，则可以结束循环了；不存在请求行、请求头包含\r的情况，所以遇到\r就可以确定是结束该行了
                // 其实可以忽略这个点，但是想规范点来...
                if (ch == 10) {
                    break;
                }
                // 虽然可以省略（因为知道它是要换行的意思），但是笔者还是想按规范的来
                throw new RuntimeException("CRLF不完整，请确保每行结尾为CRLF");
            }
            sb.append((char) ch);
        }
        return sb.toString();
    }
}
