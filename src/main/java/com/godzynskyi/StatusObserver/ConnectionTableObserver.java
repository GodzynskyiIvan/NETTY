package com.godzynskyi.StatusObserver;

import com.godzynskyi.ConnectionDTO;

public class ConnectionTableObserver {
    //Last 16 connections for connections Table
    private static FixedSizeStack<ConnectionDTO> connections = new FixedSizeStack<>(16);

    public synchronized static void update(ConnectionDTO connection) {
        connections.add(connection);
    }

    public synchronized static String getTable() {
        StringBuilder result = new StringBuilder();
        result.append("\n\n"+connections.getAll().size()+" LAST HANDLED CONNECTION(S)\n");
        result.append("_________________________________________________________________________________________________________\n");
        result.append("IP Address \t\tTime of query \t\t\tSent B \tRecieved B \tSpeed \t\tURI\n");
        result.append("_________________________________________________________________________________________________________\n");
        for(ConnectionDTO entry: connections.getAll()) {
            result.append(entry.getIp() + "\t");
            result.append(entry.getStartedDate().toString() + "\t");
            result.append(entry.getResponseBytes()+"\t");
            result.append(entry.getRequestBytes() + "\t\t");
            result.append(entry.getSpeed()+"\t");
            result.append(entry.getUri() + "\t");
            result.append("\n");
        }
        result.append("_________________________________________________________________________________________________________\n");
        return result.toString();
    }

}