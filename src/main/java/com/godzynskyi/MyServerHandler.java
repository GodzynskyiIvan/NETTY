package com.godzynskyi;

import com.godzynskyi.RequestHandlers.*;
import com.godzynskyi.StatusObserver.CountActiveChannels;
import com.godzynskyi.StatusObserver.CountOfRequestsObserver;
import com.godzynskyi.StatusSubject.SendRequestToObservers;
import com.godzynskyi.StatusSubject.SendResponseListener;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;

import java.net.InetSocketAddress;

public class MyServerHandler extends ChannelHandlerAdapter{
    private ConnectionDTO connection;
    private FullHttpResponse res;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        CountOfRequestsObserver.update(connection);
        CountActiveChannels.addChannel();

        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println("Read " + ctx.toString());
        InetSocketAddress ip = (InetSocketAddress)ctx.channel().remoteAddress();
        connection = new ConnectionDTO();

        //Handle Request
        FullHttpRequest request = (FullHttpRequest) msg;
        connection.setIp(ip);
        connection.addRequestBytes(msg.toString().length());
        connection.setUri(request.uri());
        connection.setRequestEndedTime(System.currentTimeMillis());
        SendRequestToObservers.send(connection);

        //Handle Response
        String uri = connection.getUri();
        QueryStringDecoder decoder = new QueryStringDecoder(uri);
        String path = decoder.path();

        RequestHandlerCommand requestHandlerCommand = null;
        switch(path) {
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
        res = requestHandlerCommand.process(connection);
        connection.setResponseBytes(res.content().writerIndex());
        long startTimeResponse;
        startTimeResponse = System.currentTimeMillis();
        ctx.writeAndFlush(res)
                .addListener(ChannelFutureListener.CLOSE);
        connection.setResponseTime(System.currentTimeMillis() - startTimeResponse);
//        super.channelRead(ctx, connection);
    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("ReadCompl " + ctx.toString());
//        super.channelReadComplete(ctx);
//    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        new SendResponseListener(connection);
        CountActiveChannels.releaseChannel();
        super.channelInactive(ctx);
    }

}
