package com.godzynskyi.RequestHandlers;

import com.godzynskyi.ConnectionDTO;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.util.*;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static java.lang.Thread.sleep;

/**
 * Created by JavaDeveloper on 22.07.2015.
 */
public class HelloRequestHandler implements RequestHandlerCommand {
    @Override
    public DefaultFullHttpResponse process(ConnectionDTO connection) {
        ByteBuf content = Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
        final DefaultFullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, content);
        response.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
        HttpHeaderUtil.setContentLength(response, content.readableBytes());
//        long timerTime = System.currentTimeMillis()+10000;
//        while(System.currentTimeMillis()<timerTime) {

//        }
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
}
