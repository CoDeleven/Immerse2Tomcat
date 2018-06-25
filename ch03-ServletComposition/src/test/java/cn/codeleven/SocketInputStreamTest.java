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
        assert "POST".equals(requestLine.getMethod());
        assert "/test.html".equals(requestLine.getPath());
        assert "HTTP/1.1".equals(requestLine.getProtocol());
    }
}
