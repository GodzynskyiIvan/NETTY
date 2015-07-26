package com.godzynskyi.StatusObserver;

import com.godzynskyi.ConnectionDTO;

import java.util.LinkedHashSet;
import java.util.Set;

public class CountOfUniqueRequestsObserver {
    private static Set<UniqueRequest> requests = new LinkedHashSet<>();

    public static synchronized void update(ConnectionDTO connection) {
        UniqueRequest ur = new UniqueRequest(connection.getIp().split(":")[0], connection.getUri());
        requests.add(ur);

    }

    public static int getCount() {
        return requests.size();
    }

}

class UniqueRequest {
    private String ip;
    private String uri;

    public UniqueRequest(String ip, String uri) {
        this.ip = ip;
        this.uri = uri;
    }

    public String getIp() {
        return ip;
    }

    public String getUri() {
        return uri;
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(this.getClass())) {
            return false;
        }
        UniqueRequest ur = (UniqueRequest) obj;
        return (ip.equals(ur.getIp()) && uri.equals(ur.getUri()));
    }

    @Override
    public int hashCode() {
        return ip.hashCode() * 2 + uri.hashCode() * 3;
    }
}