package com.godzynskyi.StatusSubject;

import com.godzynskyi.ConnectionDTO;
import com.godzynskyi.StatusObserver.*;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * Created by JavaDeveloper on 22.07.2015.
 */
public class SendResponseListener {

    private ConnectionDTO connection;

    public SendResponseListener(ConnectionDTO connection) {
        ConnectionTableObserver.update(connection);
    }


}
