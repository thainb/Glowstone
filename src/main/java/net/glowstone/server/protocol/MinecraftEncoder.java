package net.glowstone.server.protocol;

import net.glowstone.server.protocol.codec.MessageCodec;
import net.glowstone.server.protocol.msg.Message;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import java.io.IOException;

/**
 * A OneToOneEncode} which encodes Minecraft Messages into ChannelBuffers.
 */
public class MinecraftEncoder extends OneToOneEncoder {

    @SuppressWarnings("unchecked")
    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel c, Object msg) throws Exception {
        if (msg instanceof Message) {
            Message message = (Message) msg;

            Class<? extends Message> clazz = message.getClass();
            MessageCodec<Message> codec = (MessageCodec<Message>) MessageMap.getCodec(clazz);
            if (codec == null) {
                throw new IOException("Unknown message type: " + clazz + ".");
            }

            ChannelBuffer opcodeBuf = ChannelBuffers.buffer(1);
            opcodeBuf.writeByte(codec.getOpcode());

            return ChannelBuffers.wrappedBuffer(opcodeBuf, codec.encode(message));
        }
        return msg;
    }

}
