package com.smrt.CouponProject.utils;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {
    public static HttpEntity<String> getRequest(Map<String,Object> map) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String,Object> item:map.entrySet()) {
            jsonObject.put(item.getKey(),item.getValue());
        }
        return new HttpEntity<>(jsonObject.toString(), headers);
    }

    public static HttpEntity<String> getRequest(Map<String,Object> map, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization",token);
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String,Object> item:map.entrySet()) {
            jsonObject.put(item.getKey(),item.getValue());
        }
        return new HttpEntity<>(jsonObject.toString(), headers);
    }

    public static HttpEntity<String> getRequest(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization",token);

        return new HttpEntity<>(headers);
    }
}
