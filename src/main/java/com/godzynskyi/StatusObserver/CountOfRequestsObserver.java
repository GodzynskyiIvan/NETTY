package com.godzynskyi.StatusObserver;

import com.godzynskyi.ConnectionDTO;

/**
 * Created by JavaDeveloper on 22.07.2015.
 */
public class CountOfRequestsObserver {
    private static int count=0;

    public synchronized static void update(ConnectionDTO connection) {
        count++;
    }

    public synchronized static int getCount() {
        return count;
    }
}
