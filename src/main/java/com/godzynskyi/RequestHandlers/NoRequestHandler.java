package com.godzynskyi.RequestHandlers;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

/**
 * Return response to default request
 */
public class NoRequestHandler implements RequestHandlerCommand {
    @Override
    public DefaultFullHttpResponse process(String uri) {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT);
        return response;
    }
}