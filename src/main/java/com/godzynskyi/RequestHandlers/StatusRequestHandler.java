package com.godzynskyi.RequestHandlers;

import com.godzynskyi.StatusObserver.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Return response to "/status" request
 */
public class StatusRequestHandler implements RequestHandlerCommand {
    @Override
    public DefaultFullHttpResponse process(String uri) {

        StringBuilder content = new StringBuilder();
        content.append("COUNT OF QUERIES: " + CountOfRequestsObserver.getCount() +"\n");
        content.append("COUNT OF UNIQUE QUERIES: "+CountOfUniqueRequestsObserver.getCount()+"\n");
        content.append(IPTableObserver.getTable());
        content.append("\n");
        content.append(RedirectTableObserver.getTable());
        content.append("COUNT OF OPENING CONNECTIONS: " + CountActiveChannels.getCount()+ "\n");
        content.append(ConnectionTableObserver.getTable());

        ByteBuf byteBuf = Unpooled.copiedBuffer(content, CharsetUtil.UTF_8);
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, byteBuf);
        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
        HttpHeaderUtil.setContentLength(response, byteBuf.readableBytes());
        return response;
    }
}