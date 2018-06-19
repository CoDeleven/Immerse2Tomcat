package cn.codeleven;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 一个用于处理静态资源的类
 * 如果不用一个类封装一下，里面的细节就暴露了
 * Author: CoDeleven
 * Date: 2018/6/19
 */
public class StaticProcessor {

    public void process(Request request, Response response){
        response.setRequest(request);
        response.sendStaticResource();
    }
}
