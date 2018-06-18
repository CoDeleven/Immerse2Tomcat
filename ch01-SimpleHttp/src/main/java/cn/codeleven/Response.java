package cn.codeleven;

import java.io.*;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/18
 */
public class Response {
    private OutputStream os;
    private static final int BUFFER_SIZE = 1024;
    public Response(OutputStream os) {
        this.os = os;
    }

    private Request request;

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource(){
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            String header = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: 14\r\n" +
                    "\r\n";

            File file = new File(Server.WEB_ROOT + File.separator + request.getUri());
            if(file.exists()){
                os.write(header.getBytes());
                fis = new FileInputStream(file);
                int len = fis.read(bytes);
                while(len != -1){
                    os.write(bytes, 0, len);
                    len = fis.read(bytes);
                }
            }else{
                System.out.println("-------没有找到文件--------");
                String message = "HTTP/1.1 404 File Not Found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 23\r\n" +
                        "\r\n" +
                        "<h1>File Not Found</h1>\r\n";
                os.write(message.getBytes());
                os.flush();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(fis != null){
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
