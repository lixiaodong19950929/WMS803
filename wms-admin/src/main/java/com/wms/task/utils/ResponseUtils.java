package com.wms.task.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Data
public class ResponseUtils {
    public static Object ok() {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("status", 200);
        obj.put("message", "成功");
        obj.put("result",new ArrayList());

        return obj;
    }

}
