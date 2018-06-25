package cn.codeleven.request;

import cn.codeleven.HttpRequest;

import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 用于解析参数的Stream
 * 为什么要将SocketInputStream和RequestStream分开来？
 * 因为如果Servlet不准备获取Parameter，那么就会浪费资源
 *
 * Author: CoDeleven
 * Date: 2018/6/25
 */
public class RequestStream extends ServletInputStream {
    public int read() throws IOException {
        if(closed){
            throw new IllegalStateException("InputStream已关闭......");
        }
        if(length >= 0 && hadReadLen >= length){
            return -1;
        }
        int ch = is.read();
        if(ch < 0){
            ++hadReadLen;
        }
        return ch;
    }

    private InputStream is;
    private int length;
    private boolean closed;
    private int hadReadLen;

    public RequestStream(HttpRequest request){
        this.is = request.getOriginalStream();
        this.length = request.getContentLength();
    }

    @Override
    public void close() throws IOException {
        if(closed){
            throw new IllegalStateException("InputStream已关闭......");
        }
        while(true){
            int ch = is.read();
            if(ch < 0){
                break;
            }
        }
        closed = true;
    }
}
