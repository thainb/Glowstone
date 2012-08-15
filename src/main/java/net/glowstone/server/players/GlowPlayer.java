package net.glowstone.server.players;

import net.minecraft.workbench.server.entities.Entity;
import net.minecraft.workbench.server.players.Player;

import java.util.UUID;

/**
 * A connected player which may or may not be controlling an entity.
 */
public class GlowPlayer implements Player {

    /**
     * The UUID of the player.
     */
    private final UUID uuid;

    /**
     * The username of the player.
     */
    private final String username;

    /**
     * The mutable display name of the player.
     */
    private String displayName;

    /**
     * The entity this player is controlling.
     */
    private Entity playerEntity;

    public GlowPlayer(UUID uuid, String username, Entity playerEntity) {
        this.uuid = uuid;
        this.username = username;
        this.displayName = username;
        this.playerEntity = playerEntity;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Entity getControlledEntity() {
        return playerEntity;
    }

}
