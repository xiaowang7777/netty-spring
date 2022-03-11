package com.wjf.github.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDecoder<T> extends ByteToMessageDecoder implements Decoder<T> {

    private static final List<AbstractDecoder<Object>> decoderList = new ArrayList<>();


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        for (AbstractDecoder<Object> decoder : decoderList) {
            byte[] byteArray = decoder.getByteArray(byteBuf);

            if (decoder.verify(byteArray)) {
                list.add(decoder.decode(byteArray));
            }
        }
    }

    public static class DefaultDecoder extends AbstractDecoder<Object> {
        @Override
        public byte[] getByteArray(ByteBuf byteBuf) {
            return new byte[0];
        }

        @Override
        public boolean verify(byte[] msg) {
            return true;
        }

        @Override
        public Object decode(byte[] msg) {
            return null;
        }
    }

    public static void addDecoder(AbstractDecoder<Object> decoder) {
        decoderList.add(decoder);
    }
}
