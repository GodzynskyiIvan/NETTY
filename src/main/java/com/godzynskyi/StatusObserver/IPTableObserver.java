package com.godzynskyi.StatusObserver;

import com.godzynskyi.ConnectionDTO;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JavaDeveloper on 22.07.2015.
 */
public class IPTableObserver {
    private static Map<String, IPStatistic> ipStatictics = new HashMap<String, IPStatistic>();

    public static synchronized void update(ConnectionDTO connection) {
        IPStatistic ipStatistic = ipStatictics.get(connection.getIp().getHostString());
        if (ipStatistic == null) {
            ipStatictics.put(connection.getIp().getHostString(), new IPStatistic(connection.getStartedTime()));
        } else {
            ipStatistic.incrementCountRequest();
            ipStatistic.setLastRequestTime(connection.getStartedTime());
            ipStatictics.put(connection.getIp().getHostString(), ipStatistic);
        }
    }

    public synchronized static String getTableWithHTMLTags() {
        StringBuilder result = new StringBuilder();
        result.append("<table border=\"1\" width=\"400\">" +
                "<caption>Count of request from IP</caption>" +
                "<thead>" +
                "<tr>" +
                "<th>IP Address</th>" +
                "<th>Count of query</th>" +
                "<th>Time of last query</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>");
        for (Map.Entry<String, IPStatistic> entry : ipStatictics.entrySet()) {
            result.append("<tr>" +
                    "<td>" + entry.getKey() + "</td>" +
                    "<td>" + entry.getValue().countRequest + "</td>" +
                    "<td>" + entry.getValue().getLastRequestTime().toString() + "</td>" +
                    "</tr>");
        }
        result.append("</tbody>" +
                "</table>");
        return result.toString();
    }
}
class IPStatistic {
    int countRequest = 0;
    long lastRequestTime = 0;

    public IPStatistic(long lastRequestTime) {
        countRequest = 1;
        this.lastRequestTime = lastRequestTime;
    }


    public void incrementCountRequest() {
        countRequest++;
    }

    public Date getLastRequestTime() {
        return new Date(lastRequestTime);
    }

    public void setLastRequestTime(long lastRequestTime) {
        this.lastRequestTime = lastRequestTime;
    }
}
