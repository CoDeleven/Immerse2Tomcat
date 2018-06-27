package cn.codeleven.response;

import cn.codeleven.HttpResponse;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/21
 */
public class ResponseStream extends ServletOutputStream {
    private boolean autoCommitted;
    private HttpResponse response;
    private OutputStream outputStream;
    private boolean isFirst = true;
    public ResponseStream(HttpResponse response) {
        this.response = response;
        this.outputStream = response.getOriginalOutputStream();
    }

    @Override
    public void write(int b) throws IOException {
/*        if(isFirst){
            response.sendHeaders();
            isFirst = false;
        }*/
        outputStream.write(b);
    }


}
