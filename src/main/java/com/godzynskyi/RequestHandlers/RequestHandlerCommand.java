package com.godzynskyi.RequestHandlers;

import com.godzynskyi.ConnectionDTO;
import io.netty.handler.codec.http.DefaultFullHttpResponse;

/**
 * Created by JavaDeveloper on 22.07.2015.
 */
public interface RequestHandlerCommand {
    public DefaultFullHttpResponse process(ConnectionDTO connection);
}
