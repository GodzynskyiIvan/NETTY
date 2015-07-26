package com.godzynskyi.StatusObserver;

public class CountActiveChannels {
    private volatile static int count=0;

    public synchronized static void addChannel() {
            count++;
    }

    public synchronized static void releaseChannel() {
            count--;
    }

    public static int getCount() {
        return count;
    }
}
