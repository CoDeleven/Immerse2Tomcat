package cn.codeleven;

import cn.codeleven.request.ParameterMap;
import cn.codeleven.request.RequestUtil;
import org.junit.Test;

import java.util.Map;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/21
 */
public class RequestUtilTest {
    @Test
    public void testParseRequest(){
        Map map = new ParameterMap();
        RequestUtil.parseRequest(map, "name=foo&a=123");
        map.forEach((o, o2) -> System.out.println("key:" + o + ", value:" + o2));
    }
}
