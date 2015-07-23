package com.godzynskyi;

import java.net.InetSocketAddress;
import java.util.Date;

/**
 * Created by JavaDeveloper on 22.07.2015.
 */
public class ConnectionDTO {
    private long startedTime;
    private long requestEndedTime;
    private long responseTime;
    private InetSocketAddress ip;
    private String uri;
    private int requestBytes=0;
    private int responseBytes;

    public ConnectionDTO() {
        startedTime = System.currentTimeMillis();
    }

    public long getStartedTime() {
        return startedTime;
    }

    public Date getStartedDate() {
        return new Date(startedTime);
    }

    public void setRequestEndedTime(long requestTime) {
        this.requestEndedTime = requestTime;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public InetSocketAddress getIp() {
        return ip;
    }

    public void setIp(InetSocketAddress ip) {
        this.ip = ip;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getRequestBytes() {
        return requestBytes;
    }

    public void addRequestBytes(int requestBytes) {
        this.requestBytes += requestBytes;
    }

    public int getResponseBytes() {
        return responseBytes;
    }

    public void setResponseBytes(int responseBytes) {
        this.responseBytes = responseBytes;
    }

    public String getSpeed() {
        long requestTime = requestEndedTime-startedTime;
        long time = requestTime + responseTime;
        if(time==0) return "time=>0";
        double result = ((double)(requestBytes + responseBytes)) / ((double)(requestTime + responseTime)) * 1000;
        return String.format("%.2f",result);
    }

    @Override
    public String toString() {
        return "IP: " + ip.toString() + "\n" +
                "URI: " + uri + "\n" +
                "Started time: " + startedTime + "\n" +
                "Request time: " +(requestEndedTime - startedTime) + "\n" +
                "Request bytes" + requestBytes + "\n" +
                "Response time" + responseTime + "\n" +
                "Response bytes" + responseBytes + "\n";
     }

}
