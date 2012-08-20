package net.glowstone.server.protocol.codec;

import net.glowstone.server.protocol.msg.Message;
import org.jboss.netty.buffer.ChannelBuffer;

import java.io.IOException;

public abstract class MessageCodec<T extends Message> {

    private final int opcode;

    public MessageCodec(int opcode) {
        this.opcode = opcode;
    }

    public final int getOpcode() {
        return opcode;
    }

    public abstract ChannelBuffer encode(T message) throws IOException;

    public abstract T decode(ChannelBuffer buffer) throws IOException;

}
