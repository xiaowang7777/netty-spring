package com.wjf.github.nettyserver;

import com.wjf.github.builder.Builder;
import com.wjf.github.server.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractNettyServer implements Server {

    private static final Logger logger = LoggerFactory.getLogger(AbstractNettyServer.class);

    private final AtomicInteger status = new AtomicInteger(Server.stop);
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workGroup;
    private final ServerBootstrap bootstrap;
    private final SocketAddress socketAddress;
    private final NettyServerChannelInitializer serverChannelInitializer;

    private Channel serverChannel;

    AbstractNettyServer(int bossThread, int workThread, SocketAddress socketAddress, NettyServerChannelInitializer serverChannelInitializer) {
        this.bossGroup = new NioEventLoopGroup(bossThread);
        this.workGroup = new NioEventLoopGroup(workThread);
        this.bootstrap = new ServerBootstrap();
        this.socketAddress = socketAddress;
        this.serverChannelInitializer = serverChannelInitializer;
    }

    protected abstract Class<? extends ServerChannel> serverChannel();

    @Override
    public void start() throws Exception {
        while (!this.updateNettyServerStatus(Server.starting)) {

        }
        logger.warn("staring netty server.");
        ChannelFuture channelFuture = bootstrap
                .group(bossGroup, workGroup)
                .channel(serverChannel())
                .childHandler(serverChannelInitializer.get())
                .bind(socketAddress)
                .sync();

        while (!this.updateNettyServerStatus(Server.running)) {

        }
        logger.info("started netty server successfully! server bind at {}", socketAddress.toString());
        serverChannel = channelFuture.channel();
        channelFuture.channel().closeFuture().sync();
        while (!this.updateNettyServerStatus(Server.stop)) {

        }
    }

    @Override
    public void stop() throws Exception {
        if (!isRunning()) {
            throw new IllegalStateException("netty server is not started.");
        }
        logger.warn("stopping netty server.");
        Future<?> bossGroupShutdownFuture = bossGroup.shutdownGracefully();
        bossGroupShutdownFuture.await(3, TimeUnit.SECONDS);
        Future<?> workGroupShutdownFuture = workGroup.shutdownGracefully();
        workGroupShutdownFuture.await(3, TimeUnit.SECONDS);
        serverChannel.close();
        while (!updateNettyServerStatus(Server.stop)) {

        }
        logger.warn("netty server stopped successfully.");
    }

    @Override
    public void restart() throws Exception {
        stop();
        start();
    }

    @Override
    public int status() {
        return status.get();
    }

    protected boolean updateNettyServerStatus(int status) {
        return updateNettyServerStatus(this.status(), status);
    }

    protected boolean updateNettyServerStatus(int oldStatus, int newStatus) {
        return this.status.compareAndSet(oldStatus, newStatus);
    }

    public static class NettyServerBuilder implements Builder<AbstractNettyServer> {

        private int bossThread = 1;
        private int workThread = 1;
        private String bindHost = "0.0.0.0";
        private int bindPort = 8080;
        private NettyServerChannelInitializer serverChannelInitializer = () -> null;
        private NettyServerKind serverKind = NettyServerKind.NIO;

        NettyServerBuilder() {
        }

        public NettyServerBuilder bossThreadCount(int threadCount) {
            this.bossThread = threadCount;
            return NettyServerBuilder.this;
        }

        public NettyServerBuilder workThreadCount(int threadCount) {
            this.workThread = threadCount;
            return NettyServerBuilder.this;
        }

        public NettyServerBuilder bindHost(String host) {
            this.bindHost = host;
            return NettyServerBuilder.this;
        }

        public NettyServerBuilder bindPort(int port) {
            this.bindPort = port;
            return NettyServerBuilder.this;
        }

        public NettyServerBuilder serverChannelInitializer(NettyServerChannelInitializer serverChannelInitializer){
            this.serverChannelInitializer = serverChannelInitializer;
            return NettyServerBuilder.this;
        }

        public NettyServerBuilder serverKind(NettyServerKind serverKind) {
            this.serverKind = serverKind;
            return NettyServerBuilder.this;
        }

        @Override
        public AbstractNettyServer build() {

            InetAddress address = null;
            try {
                address = InetAddress.getByName(this.bindHost);
            } catch (UnknownHostException e) {
                e.printStackTrace();
                logger.error("parse host: {} fail!", this.bindHost);
                System.exit(-1);
            }

            SocketAddress socketAddress = new InetSocketAddress(address, this.bindPort);

            switch (this.serverKind) {
                case NIO:
                    return new NioNettyServer(this.bossThread, this.workThread, socketAddress, serverChannelInitializer);
                case BIO:
                case POLL:
                case EPOLL:
                default:
                    throw new RuntimeException();
            }
        }
    }


    public static NettyServerBuilder newBuilder() {
        return new NettyServerBuilder();
    }
}
