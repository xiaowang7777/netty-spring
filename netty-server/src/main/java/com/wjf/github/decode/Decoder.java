package com.wjf.github.decode;

import io.netty.buffer.ByteBuf;

public interface Decoder<T> {

    byte[] getByteArray(ByteBuf byteBuf);

    boolean verify(byte[] msg);

    T decode(byte[] msg);

}
