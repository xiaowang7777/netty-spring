package com.wjf.github.example.bean;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@ChannelHandler.Sharable
@Component
public class ServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest defaultFullHttpRequest) throws Exception {
        String uri = defaultFullHttpRequest.uri();
        System.out.println(uri);
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK, Unpooled.copiedBuffer("hello", StandardCharsets.UTF_8));
        channelHandlerContext.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
