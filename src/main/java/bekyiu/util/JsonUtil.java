package bekyiu.util;

import com.alibaba.fastjson.JSONObject;

public class JsonUtil
{
    //0表示正常
    public static String getJsonString(int status, String msg)
    {
        JSONObject json = new JSONObject();
        json.put("code", status);
        json.put("msg", msg);
        return json.toJSONString();
    }
    public static String getJsonString(int status)
    {
        JSONObject json = new JSONObject();
        json.put("code", status);
        return json.toJSONString();
    }
}
