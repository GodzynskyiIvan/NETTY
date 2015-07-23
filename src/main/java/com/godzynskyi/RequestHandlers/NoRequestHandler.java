package com.godzynskyi.RequestHandlers;

import com.godzynskyi.ConnectionDTO;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

/**
 * Created by JavaDeveloper on 22.07.2015.
 */
public class NoRequestHandler implements RequestHandlerCommand {
    @Override
    public DefaultFullHttpResponse process(ConnectionDTO connection) {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT);
        return response;
    }
}
