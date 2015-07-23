package com.godzynskyi.StatusObserver;

import com.godzynskyi.ConnectionDTO;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JavaDeveloper on 22.07.2015.
 */
public class RedirectTableObserver {
    private static Map<String, Integer> redirectStatistics = new HashMap<String, Integer>();

    public static synchronized void update(ConnectionDTO connection) {
        QueryStringDecoder decoder = new QueryStringDecoder(connection.getUri());
        if(decoder.path().toLowerCase().equals("/redirect")) {
            String url = decoder.parameters().get("url").get(0);
            int countOfThisRedirect = 0;
            if (url!=null && redirectStatistics.containsKey(url)) {
                countOfThisRedirect = redirectStatistics.get(url);
            }
            redirectStatistics.put(url,++countOfThisRedirect);
        }
    }

    public synchronized static String getTableWithHTMLTags() {
        if(redirectStatistics.size()==0) return "";
        StringBuilder result = new StringBuilder();
        result.append("<table border=\"1\" width=\"400\">"+
                "<caption>Count of redirect queries by URL</caption>" +
                "<thead>" +
                "<tr>" +
                "<th>URL</th>" +
                "<th>Count of redirect queriies</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>");
        for(Map.Entry<String, Integer> entry: redirectStatistics.entrySet()) {
            result.append("<tr>" +
                    "<td>"+entry.getKey()+"</td>" +
                    "<td>"+entry.getValue()+"</td>" +
                    "</tr>");
        }
        result.append("</tbody>" +
                "</table>");
        return result.toString();
    }
}
