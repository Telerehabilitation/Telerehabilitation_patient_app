package com.example.telerehabilitationpatientapp.data.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class DataSource {
    private static final String host = "http://192.168.1.3"; // Change on different environment
    private static final String port = "8000";

    private String generateUrl(String[] keys, Map<String, String> getParams) {
        StringBuilder urlBuilder = new StringBuilder(host + ":" + port);
        for (String key:
             keys) {
            urlBuilder.append("/")
                    .append(key);
        }

        if (!getParams.isEmpty()) {
            urlBuilder.append('?');
            Iterator iterator = getParams.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry paramPair = (Map.Entry)iterator.next();
                urlBuilder.append(paramPair.getKey())
                        .append("=")
                        .append(paramPair.getValue());
                if (iterator.hasNext()) {
                    urlBuilder.append("&");
                }
                iterator.remove();
            }
        }
        return urlBuilder.toString();
    }

    public String getUrl(String[] keys) {
        return this.generateUrl(keys, new HashMap<String, String>());
    }

    public String getUrl(String[] keys, Map<String, String> getParams) {
        return this.generateUrl(keys, getParams);
    }
}
