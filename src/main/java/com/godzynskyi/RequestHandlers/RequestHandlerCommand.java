package com.godzynskyi.RequestHandlers;

import io.netty.handler.codec.http.DefaultFullHttpResponse;

/**
 * Pattern command
 * Return response by request uri
 */
public interface RequestHandlerCommand {
    public DefaultFullHttpResponse process(String uri);
}
