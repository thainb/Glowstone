package net.glowstone.server;

import net.glowstone.server.protocol.MinecraftPipelineFactory;
import net.glowstone.server.protocol.Session;
import net.glowstone.server.protocol.SessionList;
import net.minecraft.workbench.server.Server;
import net.minecraft.workbench.server.players.BanList;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * The main server class, responsible for handling server configuration and client connectivity.
 */
public class GlowServer implements Server {

    /**
     * The BanList responsible for controlling who may not join the server.
     */
    private BanList banList = null;

    /**
     * The ServerBootstrap used to initialize Netty.
     */
    private final ServerBootstrap bootstrap = new ServerBootstrap();

    /**
     * A group containing all of the Channels the server has open.
     */
    private final ChannelGroup channelGroup = new DefaultChannelGroup();

    /**
     * The logger for server output.
     */
    private final Logger logger = Logger.getLogger("Glowstone");

    /**
     * The list of all connected clients.
     */
    private final SessionList sessionList = new SessionList();

    // =========
    // Internals
    // =========

    /**
     * Creates a new GlowServer.
     */
    public GlowServer() {
        ExecutorService executor = Executors.newCachedThreadPool();
        bootstrap.setFactory(new NioServerSocketChannelFactory(executor, executor));
        bootstrap.setPipelineFactory(new MinecraftPipelineFactory(this));
    }

    /**
     * Start the server.
     */
    public void start() {
        // TODO: read us a configuration
        channelGroup.add(bootstrap.bind(new InetSocketAddress(25565)));
    }

    /**
     * Gracefully stop the server.
     */
    public void shutdown() {
        channelGroup.close();
        bootstrap.getFactory().releaseExternalResources();
    }

    /**
     * Register a channel and session pair.
     * @param channel The channel to register.
     * @param session The session to register.
     */
    public void addChannel(Channel channel, Session session) {
        channelGroup.add(channel);
        sessionList.add(session);
    }

    /**
     * Remove a channel and session pair.
     * @param channel The channel to remove.
     * @param session The session to remove.
     */
    public void removeChannel(Channel channel, Session session) {
        channelGroup.remove(channel);
        sessionList.remove(session);
    }

    /**
     * Get the server's logger.
     * @return The logger.
     */
    public Logger getLogger() {
        return logger;
    }

    // ==========
    // Public API
    // ==========

    public BanList getBanList() {
        return banList;
    }

    public void setBanList(BanList banList) {
        this.banList = banList;
    }
}
