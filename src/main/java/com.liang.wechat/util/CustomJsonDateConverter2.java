package com.liang.wechat.util;

/**
 * Created by Je on 2016/10/29.
 */

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CustomJsonDateConverter2 extends ObjectMapper {

    @Autowired
    private HttpServletRequest request;

    private String cookieName = "TimeZone";


    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    /**
     * 通用时间处理
     * new Date().toString().match(/([A-Z]+[\+-][0-9]+)/)[1]
     */
    public CustomJsonDateConverter2() {

        SimpleModule module =  new SimpleModule();
        module.addSerializer(Date.class, new JsonSerializer<Date>() {
            @Override
            public void serialize(Date value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Cookie cookie = WebUtils.getCookie(request, cookieName);

                String timeZone = "GMT+0800";

                TimeZone localeTimeZone = null;
                if(timeZone!=null) {
                    localeTimeZone = TimeZone.getTimeZone(timeZone);
                }
                if(localeTimeZone!=null){
                    sdf.setTimeZone(localeTimeZone);
                }
                jsonGenerator.writeString(sdf.format(value));
            }
        });
        this.registerModule(module);


        /**
         * 增加处理空值，所有空值统一返回""
         */
        this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                jsonGenerator.writeString("");
            }
        });

        /**
         * 设置忽略未知字段的错误
         */
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }
}