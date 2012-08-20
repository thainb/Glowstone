package net.glowstone;

import net.glowstone.server.GlowServer;
import net.minecraft.workbench.Workbench;
import net.minecraft.workbench.WorkbenchImpl;
import net.minecraft.workbench.server.Server;

/**
 * The main class and backbone of Glowstone, implementing the Workbench.
 */
public class GlowstoneMain extends WorkbenchImpl {

    /**
     * The server
     */
    private GlowServer server;

    /**
     * The main method of Glowstone.
     *
     * @param args Command-line parameters.
     */
    public static void main(String[] args) {
        GlowstoneMain main = new GlowstoneMain();
        Workbench.setInstance(main);
        main.startServer(args);
    }

    /**
     * Starts the server.
     *
     * @param args Command-line parameters.
     */
    private void startServer(String[] args) {
        server = new GlowServer();
        server.start();
    }

    @Override
    public String getImplementationName() {
        return "Glowstone";
    }

    @Override
    public String getImplementationVersion() {
        // TODO: fancy this up
        return "0.0.1-SNAPSHOT";
    }

    @Override
    public Server getServer() {
        return server;
    }
}
