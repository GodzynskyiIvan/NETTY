package com.godzynskyi;

import java.util.Date;

/**
 * Info about completed connection
 */
public class ConnectionDTO {
    private long startTime;
    private long requestEndedTime;
    private long responseStartTime;
    private long responseEndedTime;
    private String ip;
    private String uri;
    private int requestBytes=0;
    private int responseBytes=0;

    public long getStartTime() {
        return startTime;
    }

    public Date getStartedDate() {
        return new Date(startTime);
    }

    public synchronized ConnectionDTO setRequestEndedTime(long requestTime) {
        this.requestEndedTime = requestTime;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public synchronized ConnectionDTO setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getUri() {
        return uri;
    }

    public synchronized ConnectionDTO setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public int getRequestBytes() {
        return requestBytes;
    }

    public synchronized ConnectionDTO addRequestBytes(int requestBytes) {
        this.requestBytes += requestBytes;
        return this;
    }

    public int getResponseBytes() {
        return responseBytes;
    }

    public synchronized ConnectionDTO addResponseBytes(int responseBytes) {
        this.responseBytes += responseBytes;
        return this;
    }

    public synchronized String getSpeed() {
        long requestTime = requestEndedTime- startTime;
        long responseTime = responseEndedTime - responseStartTime;
        long time = requestTime + responseTime;
        if(time==0) time=1;
        double result = ((double)(requestBytes + responseBytes)) / ((double)(time)) * 1000;
        return String.format("%.2f",result);
    }

    @Override
    public String toString() {
        return "IP: " + ip.toString() + "\n" +
                "URI: " + uri + "\n" +
                "Started time: " + startTime + "\n" +
                "Request time: " +(requestEndedTime - startTime) + "\n" +
                "Request bytes" + requestBytes + "\n" +
                "Response bytes" + responseBytes + "\n";
     }

    public synchronized ConnectionDTO setStartResponseTime(long l) {
        if(responseStartTime!=0) return this;
        responseStartTime = l;
        return this;
    }

    public synchronized ConnectionDTO setResponseEndedTime(long l) {
        responseEndedTime = l;
        return this;
    }

    public synchronized ConnectionDTO setStartTime(long startTime) {
        this.startTime = startTime;
        return this;
    }
}