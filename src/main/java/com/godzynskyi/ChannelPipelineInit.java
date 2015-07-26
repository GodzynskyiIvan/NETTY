package com.godzynskyi;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


public class ChannelPipelineInit extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new StatisticHandler());
//        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new RequestToResponseHandler());
    }
}
