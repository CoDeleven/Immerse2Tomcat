package cn.codeleven;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.*;

/**
 * Server
 * Author: CoDeleven
 * Date: 2018/6/18
 */
public class Server {
    static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
    private StaticProcessor staticProcessor = new StaticProcessor();
    private ServletProcessor servletProcessor = new ServletProcessor();
    public void await() throws IOException {
        // 监听8080端口
        ServerSocket socket = new ServerSocket(8080);
        while(true){
            // 同步阻塞等待请求
            Socket conn = socket.accept();
            Request request = new Request(conn.getInputStream());
            request.parse();

            Response response = new Response(conn.getOutputStream());

            try {
                ensureServlet(request, response);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (ServletException e) {
                e.printStackTrace();
            }

            conn.close();
        }
    }
    public static void main(String[] args) throws IOException {
        new Server().await();
    }

    private void ensureServlet(Request request, Response response) throws ClassNotFoundException, IllegalAccessException, InstantiationException, ServletException, IOException {
        if(request.getUri().contains("servlet")){
            servletProcessor.process(request, response);
        }else{
            staticProcessor.process(request, response);
        }

    }

}
