package com.godzynskyi.StatusObserver;

public class CountOfRequestsObserver {
    private static int count=0;

    public synchronized static void update() {
        count++;
    }

    public static int getCount() {
        return count;
    }
}