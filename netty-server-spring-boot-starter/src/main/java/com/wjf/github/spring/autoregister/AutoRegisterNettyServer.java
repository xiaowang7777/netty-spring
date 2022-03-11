package com.wjf.github.spring.autoregister;

import com.wjf.github.nettyserver.AbstractNettyServer;
import com.wjf.github.nettyserver.NettyServerChannelInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoRegisterNettyServer {
    private NettyServerProperties nettyServerProperties;

    public NettyServerProperties getNettyServerProperties() {
        return nettyServerProperties;
    }

    @Autowired
    public void setNettyServerProperties(NettyServerProperties nettyServerProperties) {
        this.nettyServerProperties = nettyServerProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public NettyServerChannelInitializer mapHandler() {
        return () -> null;
    }

    @Bean
    public ApplicationListener<ApplicationStartedEvent> eventListener(NettyServerChannelInitializer initializer) {
        return event -> {
            try {
                AbstractNettyServer.newBuilder()
                        .bossThreadCount(nettyServerProperties.getBossThreadCount())
                        .workThreadCount(nettyServerProperties.getWorkThreadCount())
                        .bindHost(nettyServerProperties.getBindHost())
                        .bindPort(nettyServerProperties.getPort())
                        .serverKind(nettyServerProperties.getServerKind())
                        .serverChannelInitializer(initializer)
                        .build()
                        .start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
