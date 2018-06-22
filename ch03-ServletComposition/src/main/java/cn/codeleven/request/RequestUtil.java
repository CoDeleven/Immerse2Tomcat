package cn.codeleven.request;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class info
 * Author: CoDeleven
 * Date: 2018/6/21
 */
public class RequestUtil {
    public static void parseRequest(Map<String, String> parameterMap, String data){
        String[] without1 = data.split("&");

        for (String s : without1) {
            String[] result = s.split("=");
            parameterMap.put(result[0], result[1]);
        }

    }

}
