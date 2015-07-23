package com.godzynskyi.StatusObserver;

/**
 * Created by JavaDeveloper on 22.07.2015.
 */
public class CountActiveChannels {
    private static int count=0;

    public static synchronized void addChannel() {
            count++;
    }

    public static synchronized void releaseChannel() {
            count--;
    }

    public static synchronized int getCount() {
        return count;
    }
}
