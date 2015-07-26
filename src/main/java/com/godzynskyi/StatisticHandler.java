package com.godzynskyi;

import com.godzynskyi.StatusObserver.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;


/**
 * Gives information to observers used in "/status" request.
 * Count of connections, Count of requests, Tables etc.
 */
public class StatisticHandler extends ChannelHandlerAdapter {
    private ThreadLocal<ConnectionDTO> connection = new ThreadLocal<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        CountActiveChannels.addChannel();

        super.channelActive(ctx);
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {

        CountOfRequestsObserver.update();

        connection.set(new ConnectionDTO().setStartTime(System.currentTimeMillis()));
//        connection.set(connection.get().setRequestEndedTime(System.currentTimeMillis()));
        connection.set(addRequestInfoToConnectionDto(ctx, (ByteBuf) msg, connection.get()));

        CountOfUniqueRequestsObserver.update(connection.get());
        IPTableObserver.update(connection.get());
        RedirectTableObserver.update(connection.get());

        ctx.bind(ctx.channel().localAddress()).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                connection.set(connection.get().setRequestEndedTime(System.currentTimeMillis()));
                ctx.fireChannelRead(msg);
            }
        });
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

        connection.set(connection.get()
                .addResponseBytes(((ByteBuf) msg).readableBytes())
                .setStartResponseTime(System.currentTimeMillis()));

        ctx.write(msg,promise).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                connection.set(connection.get().setResponseEndedTime(System.currentTimeMillis()));
            }
        });
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {

        connection.set(connection.get().setResponseEndedTime(System.currentTimeMillis()));
        ConnectionTableObserver.update(connection.get());

        super.close(ctx, promise);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        CountActiveChannels.releaseChannel();

        super.channelInactive(ctx);
    }


    //This method update IP, requestBytes, uri
    private ConnectionDTO addRequestInfoToConnectionDto(ChannelHandlerContext ctx, ByteBuf byteBuf, ConnectionDTO connection) {
        String connectionIP = ctx.channel().remoteAddress().toString();

        int requestBytes = byteBuf.readableBytes();

        int countOfSpaces = 0;
        StringBuilder req = new StringBuilder();
        int index = 0;

        //Getting string between first and second spaces
        while (countOfSpaces < 2) {

            //LOG
            if(requestBytes<index) {
                System.out.println(byteBuf.array().toString());
            }

            int i = byteBuf.getUnsignedByte(index);
            char c;
            if (i <= 0x1f || i >= 0x7f) {
                c = '.';
            } else {
                c = (char) i;
            }
            if (i == ' ') {
                countOfSpaces++;
            }
            if (countOfSpaces == 1 && c != ' ') {
                req.append(c);
            }
            index++;
        }

        return connection
                .setStartTime(System.currentTimeMillis())
                .setIp(connectionIP)
                .addRequestBytes(requestBytes)
                .setUri(req.toString());

    }
}