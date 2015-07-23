package com.godzynskyi.StatusObserver;

import com.godzynskyi.ConnectionDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by JavaDeveloper on 22.07.2015.
 */
public class ConnectionTableObserver {
    //Last 16 connections for connections Table
    private static FixedSizeStack<ConnectionDTO> connections = new FixedSizeStack<>(16);

    public synchronized static void update(ConnectionDTO connection) {
        connections.add(connection);
    }

    public synchronized static String getTableWithHTMLTags() {
        StringBuilder result = new StringBuilder();
        result.append("<table border=\"1\" width=\"800\">"+
                "<caption>"+connections.getAll().size()+" last handled connection(s)</caption>" +
                "<thead>" +
                "<tr>" +
                "<th>IP Address</th>" +
                "<th>URI</th>" +
                "<th>Time of query</th>" +
                "<th>Sent bytes</th>" +
                "<th>Received bytes</th>" +
                "<th>Speed (bytes/sec)</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>");
        for(ConnectionDTO entry: connections.getAll()) {
            result.append("<tr>" +
                    "<td>" + entry.getIp().toString() + "</td>" +
                    "<td>" + entry.getUri() + "</td>" +
                    "<td>" + entry.getStartedDate().toString() + "</td>" +
                    "<td>" + entry.getRequestBytes()+"</td>" +
                    "<td>" + entry.getResponseBytes() + "</td>" +
                    "<td>" + entry.getSpeed()+"</td>" +
                    "</tr>");
        }
        result.append("</tbody>" +
                "</table>");
        return result.toString();
    }
}
