package com.wjf.github.nettyserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import java.util.function.Supplier;

public interface NettyServerChannelInitializer extends Supplier<ChannelInitializer<SocketChannel>> {
}
