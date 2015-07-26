package com.godzynskyi.StatusObserver;

import com.godzynskyi.ConnectionDTO;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.HashMap;
import java.util.Map;

public class RedirectTableObserver {
    private static Map<String, Integer> redirectStatistics = new HashMap<String, Integer>();

    public static void update(ConnectionDTO connection) {
        QueryStringDecoder decoder = new QueryStringDecoder(connection.getUri());
        if(decoder.path().toLowerCase().equals("/redirect")) {
            String url = decoder.parameters().get("url").get(0);
            int countOfThisRedirect = 0;
            synchronized (redirectStatistics) {
                if (url != null && redirectStatistics.containsKey(url)) {
                    countOfThisRedirect = redirectStatistics.get(url);
                }
                redirectStatistics.put(url, ++countOfThisRedirect);
            }
        }
    }

    public synchronized static String getTable() {
        if(redirectStatistics.size()==0) return "";
        StringBuilder result = new StringBuilder();
        result.append("\nCOUNT OF REDIRECT QUERIES BY URL\n");
        result.append("__________________________________________________\n");
        result.append("Count of redirect queries\tURL\n");
        result.append("__________________________________________________\n");
        for(Map.Entry<String, Integer> entry: redirectStatistics.entrySet()) {
            result.append("\t" + entry.getValue()+"\t\t\t");
            result.append(entry.getKey()+"\t");
            result.append("\n");
        }
        result.append("__________________________________________________\n\n\n");
        return result.toString();
    }

}
