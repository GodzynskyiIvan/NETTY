package com.godzynskyi.StatusObserver;

import com.godzynskyi.ConnectionDTO;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class IPTableObserver {
    private static Map<String, IPStatistic> ipStatictics = new HashMap<String, IPStatistic>();

    public static synchronized void update(ConnectionDTO connection) {
        String IP = connection.getIp().split(":")[0];

        IPStatistic ipStatistic = ipStatictics.get(IP);
        if (ipStatistic == null) {
            ipStatictics.put(IP, new IPStatistic(connection.getStartTime()));
        } else {
            ipStatistic.incrementCountRequest();
            ipStatistic.setLastRequestTime(connection.getStartTime());
            ipStatictics.put(IP, ipStatistic);
        }
    }

    public synchronized static String getTable() {
        StringBuilder result = new StringBuilder();
        result.append("\nCOUNT OF QUERIES FROM IP\n");
        result.append("_____________________________________________________________________\n");
        result.append("IP Address\tCount of queries\tTime of last query\n");
        result.append("_____________________________________________________________________\n");
        for (Map.Entry<String, IPStatistic> entry : ipStatictics.entrySet()) {
            result.append(entry.getKey() + "\t");
            result.append(entry.getValue().countRequest + "\t\t\t");
            result.append(entry.getValue().getLastRequestTime().toString() + "\n");
        }
        result.append("_____________________________________________________________________\n\n");
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