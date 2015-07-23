package com.godzynskyi.StatusSubject;

import com.godzynskyi.ConnectionDTO;
import com.godzynskyi.StatusObserver.CountOfRequestsObserver;
import com.godzynskyi.StatusObserver.CountOfUniqueRequestsObserver;
import com.godzynskyi.StatusObserver.IPTableObserver;
import com.godzynskyi.StatusObserver.RedirectTableObserver;

/**
 * Created by JavaDeveloper on 23.07.2015.
 */
public class SendRequestToObservers {

    public static void send(ConnectionDTO connection) {
//        System.out.println("Req Send");
//        CountOfRequestsObserver.update(connection);
        CountOfUniqueRequestsObserver.update(connection);
        IPTableObserver.update(connection);
        RedirectTableObserver.update(connection);
    }
}
