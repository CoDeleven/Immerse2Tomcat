package cn.codeleven;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server
 * Author: CoDeleven
 * Date: 2018/6/18
 */
public class Server {
    static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
    public static void main(String[] args) throws IOException {
        // 监听8080端口
        ServerSocket socket = new ServerSocket(8080);
        while(true){
            // 同步阻塞等待请求
            Socket conn = socket.accept();
            Request request = new Request(conn.getInputStream());
            request.parse();

            Response response = new Response(conn.getOutputStream());
            response.setRequest(request);
            response.sendStaticResource();
            conn.close();
        }
    }
}
