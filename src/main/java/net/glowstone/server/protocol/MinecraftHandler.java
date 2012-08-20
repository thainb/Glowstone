package net.glowstone.server.protocol;

import net.glowstone.server.GlowServer;
import net.glowstone.server.protocol.msg.Message;
import org.jboss.netty.channel.*;

import java.util.logging.Level;

/**
 * A SimpleChannelUpstreamHandler which processes incoming network events.
 */
public class MinecraftHandler extends SimpleChannelUpstreamHandler {

    /**
     * The server.
     */
    private final GlowServer server;

    /**
     * Creates a new network event handler.
     * @param server The server.
     */
    public MinecraftHandler(GlowServer server) {
        this.server = server;
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
        Channel c = e.getChannel();

        Session session = new Session(server, c);
        ctx.setAttachment(session);
        server.addChannel(c, session);

        server.getLogger().info("Channel connected: " + c + ".");
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
        Channel c = e.getChannel();

        Session session = (Session) ctx.getAttachment();
        server.removeChannel(c, session);
        session.dispose();

        server.getLogger().info("Channel disconnected: " + c + ".");
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        Session session = (Session) ctx.getAttachment();
        session.messageReceived((Message) e.getMessage());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        Channel c = e.getChannel();
        if (c.isOpen()) {
            server.getLogger().log(Level.WARNING, "Exception caught, closing channel: " + c + "...", e.getCause());
            c.close();
        }
    }

}
