package com.wjf.github.nettyserver;

import io.netty.channel.ServerChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;

public class NioNettyServer extends AbstractNettyServer {

    private final static Logger logger = LoggerFactory.getLogger(NioNettyServer.class);

    NioNettyServer(int bossThread, int workThread, SocketAddress socketAddress, NettyServerChannelInitializer serverChannelInitializer) {
        super(bossThread, workThread, socketAddress, serverChannelInitializer);
    }

    @Override
    protected Class<? extends ServerChannel> serverChannel() {
        return NioServerSocketChannel.class;
    }
}
