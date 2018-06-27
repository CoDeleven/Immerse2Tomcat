package cn.codeleven;

import cn.codeleven.processor.HttpProcessor;
import org.junit.Test;

import java.net.Socket;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/27
 */
public class HttpProcessorTest {
    @Test
    public void testMultiThread(){
        HttpProcessor processor = new HttpProcessor(null);
        new Thread(processor).start();

        Socket socket = new Socket();
        processor.assign(socket);
        socket = new Socket();
        processor.assign(new Socket());
    }
}
