package net.glowstone.server.protocol.handler;

import net.glowstone.server.players.GlowPlayer;
import net.glowstone.server.protocol.Session;
import net.glowstone.server.protocol.msg.Message;

public abstract class MessageHandler<T extends Message> {

    public abstract void handle(Session session, GlowPlayer player, T message);

}
