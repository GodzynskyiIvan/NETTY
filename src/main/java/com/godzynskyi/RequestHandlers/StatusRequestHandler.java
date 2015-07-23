package com.godzynskyi.RequestHandlers;

import com.godzynskyi.ConnectionDTO;
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
 * Created by JavaDeveloper on 22.07.2015.
 */
public class StatusRequestHandler implements RequestHandlerCommand {
    @Override
    public DefaultFullHttpResponse process(ConnectionDTO connection) {
//        System.out.println("CountOfRequestsObserver = " + CountOfRequestsObserver.getCount());
//        System.out.println("CountOfUniqueRequestsObserver = " + CountOfUniqueRequestsObserver.getCount());
//        System.out.println("IPTableObserver: " + IPTableObserver.getTable().toString());
//        System.out.println("RedirectRequestHandler: " + RedirectTableObserver.getTable().toString());
//        System.out.println("CountActiveChannels = " + CountActiveChannels.getCount());
//        System.out.println("ConnectionTableObserver = " + ConnectionTableObserver.getTable());
        StringBuilder content = new StringBuilder();
        content.append("<HTML>" +
                "<BODY>" +
                "<h3>Count of Query: " + CountOfRequestsObserver.getCount() +"</h3>" +
                "<h3>Count of Unique Query: "+CountOfUniqueRequestsObserver.getCount()+"</h3>");
        content.append(IPTableObserver.getTableWithHTMLTags());
        content.append("<br>");
        content.append(RedirectTableObserver.getTableWithHTMLTags());
        content.append("<h3>Count of opening connections: " + CountActiveChannels.getCount()+ "</h3>");
        content.append(ConnectionTableObserver.getTableWithHTMLTags());
        ByteBuf byteBuf = Unpooled.copiedBuffer(content, CharsetUtil.UTF_8);
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, byteBuf);
        response.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
        HttpHeaderUtil.setContentLength(response, byteBuf.readableBytes());
        return response;
    }
}
