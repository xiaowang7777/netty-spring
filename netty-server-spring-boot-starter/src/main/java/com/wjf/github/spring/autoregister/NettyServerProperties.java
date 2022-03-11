package com.wjf.github.spring.autoregister;

import com.wjf.github.nettyserver.NettyServerKind;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "netty-server")
public class NettyServerProperties {
    private int bossThreadCount;
    private int workThreadCount;
    private String bindHost;
    private int port;
    private NettyServerKind serverKind;

    public int getBossThreadCount() {
        return bossThreadCount;
    }

    public void setBossThreadCount(int bossThreadCount) {
        this.bossThreadCount = bossThreadCount;
    }

    public int getWorkThreadCount() {
        return workThreadCount;
    }

    public void setWorkThreadCount(int workThreadCount) {
        this.workThreadCount = workThreadCount;
    }

    public String getBindHost() {
        return bindHost;
    }

    public void setBindHost(String bindHost) {
        this.bindHost = bindHost;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public NettyServerKind getServerKind() {
        return serverKind;
    }

    public void setServerKind(NettyServerKind serverKind) {
        this.serverKind = serverKind;
    }
}
