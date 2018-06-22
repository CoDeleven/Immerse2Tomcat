package cn.codeleven.response;

import cn.codeleven.Response;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/21
 */
public class ResponseStream extends ServletOutputStream {
    private boolean autoCommitted;
    private Response response;
    private OutputStream outputStream;
    private boolean isFirst = true;
    public ResponseStream(Response response) {
        this.response = response;
        this.outputStream = response.getOriginalOutputStream();
    }

    public boolean isAutoCommitted() {
        return autoCommitted;
    }

    public void setAutoCommitted(boolean autoCommitted) {
        this.autoCommitted = autoCommitted;
    }

    @Override
    public void write(int b) throws IOException {
        if(isFirst){
            writeDefaultHeader();
            isFirst = false;
        }
        outputStream.write(b);
    }


    private void writeDefaultHeader() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 OK\r\n");
        sb.append("Content-Type: ").append(response.getContentType()).append("\r\n");
        sb.append("Content-Encoding: ").append(response.getCharacterEncoding()).append("\r\n");
        sb.append("\r\n");
        outputStream.write(sb.toString().getBytes());
    }
}
