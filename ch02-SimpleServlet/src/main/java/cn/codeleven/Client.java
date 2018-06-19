package cn.codeleven;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;


public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 8080);
        OutputStream os = socket.getOutputStream();
        // 封装到PrintWriter方便写入字符串
        PrintWriter writer = new PrintWriter(os, true);
        // 发送请求给本机的8080端口
        writer.println("GET /index2.jsp HTTP/1.1");
        writer.println("Host: location:8080");
        writer.println("Connection: Close");


        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        int ch;
        while((ch = br.read()) != -1){
            sb.append((char)ch);
        }

        System.out.println(sb.toString());
        socket.close();
    }
}
