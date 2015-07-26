package com.godzynskyi.RequestHandlers;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.handler.codec.http.QueryStringDecoder;

import static io.netty.handler.codec.http.HttpHeaderNames.LOCATION;
import static io.netty.handler.codec.http.HttpResponseStatus.TEMPORARY_REDIRECT;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Return response to "/redirect?url=..." request
 */
public class RedirectRequestHandler implements RequestHandlerCommand {
    @Override
    public DefaultFullHttpResponse process(String uri) {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,TEMPORARY_REDIRECT);
        QueryStringDecoder decoder = new QueryStringDecoder(uri);
        String url = decoder.parameters().get("url").get(0);
        response.headers().add(LOCATION, url);
        HttpHeaderUtil.setContentLength(response, 0);
        return response;
    }
}