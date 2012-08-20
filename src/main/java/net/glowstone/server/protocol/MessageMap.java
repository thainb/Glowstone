package net.glowstone.server.protocol;

import net.glowstone.server.protocol.codec.*;
import net.glowstone.server.protocol.handler.*;
import net.glowstone.server.protocol.msg.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides mappings from Messages to their codecs and handlers.
 */
public class MessageMap {

    /**
     * A table from protocol opcodes to codecs.
     */
    private static final MessageCodec<?>[] opcodeTable = new MessageCodec<?>[256];

    /**
     * The map from message classes to their codecs.
     */
    private static final Map<Class<? extends Message>, MessageCodec<?>> codecs = new HashMap<Class<? extends Message>, MessageCodec<?>>();

    /**
     * The map from message classes to their handlers.
     */
    private static final Map<Class<? extends Message>, MessageHandler<?>> handlers = new HashMap<Class<? extends Message>, MessageHandler<?>>();

    private MessageMap() {
        // Can't construct me
    }

    private static <T extends Message, C extends MessageCodec<T>> void bind(Class<T> messageClass, Class<C> codecClass) throws InstantiationException, IllegalAccessException {
        MessageCodec<T> codec = codecClass.newInstance();
        opcodeTable[codec.getOpcode()] = codec;
        codecs.put(messageClass, codec);
    }

    private static <T extends Message, C extends MessageCodec<T>, H extends MessageHandler<T>> void bind(Class<T> messageClass, Class<C> codecClass, Class<H> handlerClass) throws InstantiationException, IllegalAccessException {
        bind(messageClass, codecClass);
        MessageHandler<T> handler = handlerClass.newInstance();
        handlers.put(messageClass, handler);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Message> MessageHandler<T> getHandler(Class<T> clazz) {
        return (MessageHandler<T>) handlers.get(clazz);
    }

    public static MessageCodec<?> getCodec(int opcode) {
        return opcodeTable[opcode];
    }

    @SuppressWarnings("unchecked")
    public static <T extends Message> MessageCodec<T> getCodec(Class<T> clazz) {
        return (MessageCodec<T>) codecs.get(clazz);
    }

    static {
        try {
            // TODO: obviously this isn't real, put some real ones here
            bind(Message.class, MessageCodec.class, MessageHandler.class);
        }
        catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

}
