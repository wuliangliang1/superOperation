package com.liang.wechat.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by ll.wu on 2017/6/18.
 */
public class FastJson {
    /**
     * 把json数据转换成类
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json,clazz);
    }


    /**
     * obj类转换成json String
     * @param
     * @return
     */
    public static String toJson(Object obj) {
        String result=JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue);
        return result;
    }
}
