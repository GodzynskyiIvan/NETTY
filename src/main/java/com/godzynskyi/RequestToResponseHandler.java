package com.godzynskyi;

import com.godzynskyi.RequestHandlers.*;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;

/**
 * Takes HttpRequest, handle it and "write()" HttpResponse
 */
public class RequestToResponseHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {

        if (msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;
            String uri = req.uri();

            doResponse(ctx, uri);
        }
    }


    private void doResponse(final ChannelHandlerContext ctx, String uri) {

        RequestHandlerCommand requestHandlerCommand = null;
        switch (new QueryStringDecoder(uri).path()) {
            case "/hello":
                requestHandlerCommand = new HelloRequestHandler();
                break;
            case "/redirect":
                requestHandlerCommand = new RedirectRequestHandler();
                break;
            case "/status":
                requestHandlerCommand = new StatusRequestHandler();
                break;
            default:
                requestHandlerCommand = new NoRequestHandler();
        }
        final FullHttpResponse response = requestHandlerCommand.process(uri);

        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("Main exceptionCaught");
        cause.printStackTrace();
        ctx.close();
    }

}