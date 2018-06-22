package cn.codeleven;

import cn.codeleven.request.RequestLine;
import org.junit.Test;

import java.io.ByteArrayInputStream;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/20
 */
public class SocketInputStreamTest {
    @Test
    public void testRead(){
        ByteArrayInputStream bai = new ByteArrayInputStream("POST /test.html HTTP/1.1\r\n".getBytes());
        RequestLine requestLine = new RequestLine();
        SocketInputStream socketInputStream = new SocketInputStream(bai);
        socketInputStream.readRequestLine(requestLine);
        System.out.println("Method: " + new String(requestLine.getMethod()));
        System.out.println("Path: " + new String(requestLine.getPath()));
        System.out.println("Version: " + new String(requestLine.getProtocol()));
    }
}
