package com.wjf.github.example.bean;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NettyServerChannelInitializer implements com.wjf.github.nettyserver.NettyServerChannelInitializer {
    @Autowired
    private ServerHandler serverHandler;

    @Override
    public ChannelInitializer<SocketChannel> get() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline()
                        .addLast(new HttpServerCodec())
                        .addLast(new HttpObjectAggregator(512 * 1024))
                        .addLast(serverHandler);
            }
        };
    }
}
