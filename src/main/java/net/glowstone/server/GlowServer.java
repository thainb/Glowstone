package net.glowstone.server;

import net.minecraft.workbench.server.Server;
import net.minecraft.workbench.server.players.BanList;

/**
 * The main server class, responsible for handling server configuration and client connectivity.
 */
public class GlowServer implements Server {

    /**
     * The BanList responsible for controlling who may not join the server.
     */
    private BanList banList = null;

    public BanList getBanList() {
        return banList;
    }

    public void setBanList(BanList banList) {
        this.banList = banList;
    }
}
