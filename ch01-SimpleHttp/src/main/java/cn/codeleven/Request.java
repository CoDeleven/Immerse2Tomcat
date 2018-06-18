package cn.codeleven;

import java.io.IOException;
import java.io.InputStream;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/18
 */
public class Request {
    private String uri;
    private InputStream is;
    public Request(InputStream inputStream) {
        this.is = inputStream;
    }

    public void parse(){
        byte[] bytes = new byte[2048];
        int len = -1;
        try {
            len = is.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder request = new StringBuilder();
        request.append(new String(bytes, 0, len));
        System.out.println(request.toString());
        this.uri = parseUri(request.toString());
    }

    private String parseUri(String request){
        int firstIndex = request.indexOf('/');
        int endIndex =request.indexOf(" ", firstIndex);
        return request.substring(firstIndex, endIndex);
    }

    public String getUri() {
        return this.uri;
    }
}
