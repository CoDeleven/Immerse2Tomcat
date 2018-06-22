package cn.codeleven.connector;

import cn.codeleven.processor.HttpProcessor;
import cn.codeleven.request.RequestLine;
import cn.codeleven.SocketInputStream;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/20
 */
public class HttpConnector extends Thread{
    private HttpProcessor processor = new HttpProcessor();
    public void await() throws IOException {
        // 监听8080端口
        ServerSocket socket = new ServerSocket(8080);
        while(true){
            // 同步阻塞等待请求
            Socket conn = socket.accept();

            processor.process(conn);
        }
    }

    @Override
    public void run() {
        try {
            await();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
