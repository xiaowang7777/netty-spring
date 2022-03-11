package com.wjf.github.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.ArrayList;
import java.util.List;

public class AgentByteToMessageDecoder<T> extends ByteToMessageDecoder {

    private static final List<Decoder<Object>> decoderList = new ArrayList<>();


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        for (Decoder<Object> decoder : decoderList) {
            byte[] byteArray = decoder.getByteArray(byteBuf);

            if (decoder.verify(byteArray)) {
                list.add(decoder.decode(byteArray));
            }
        }
    }

    public static void addDecoder(Decoder<Object> decoder) {
        decoderList.add(decoder);
    }
}
