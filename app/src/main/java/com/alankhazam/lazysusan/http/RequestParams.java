package com.alankhazam.lazysusan.http;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple hashmap to store HTTP request parameters and build request URLs.
 * Created by AK on 11/24/2015.
 */
public class RequestParams {

    private ConcurrentHashMap<String, String> mParamMap;

    public RequestParams() {
        mParamMap = new ConcurrentHashMap<>();
    }

    public void put(String key, String value) {
        if (key != null && value != null) {
            mParamMap.put(key, value);
        }
    }

    public void put(String key, int value) {
        if (key != null) {
            mParamMap.put(key, Integer.toString(value));
        }
    }

    public void put(String key, double value) {
        if (key != null) {
            mParamMap.put(key, Double.toString(value));
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (ConcurrentHashMap.Entry<String, String> entry : mParamMap.entrySet()) {
            if (result.length() > 0) {
                result.append("&");
            } else {
                result.append("?");
            }

            result.append(entry.getKey());
            result.append("=");
            result.append(entry.getValue());
        }

        return result.toString();
    }

    public String buildRequest(String baseURL) {
        return baseURL + toString();
    }
}
