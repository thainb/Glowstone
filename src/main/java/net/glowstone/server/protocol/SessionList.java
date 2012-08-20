package net.glowstone.server.protocol;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A list of all the sessions which provides a convenient {@link #update}
 * method to update every session in one operation.
 */
public final class SessionList {

    /**
     * A list of the sessions.
     */
    private final ConcurrentMap<Session, Boolean> sessions = new ConcurrentHashMap<Session, Boolean>();

    /**
     * Updates all the sessions.
     */
    public void update() {
        for (Session session : sessions.keySet()) {
            session.update();
        }
    }

    /**
     * Adds a new session.
     *
     * @param session The session to add.
     */
    public void add(Session session) {
        sessions.put(session, true);
    }

    /**
     * Removes a session.
     *
     * @param session The session to remove.
     */
    public void remove(Session session) {
        sessions.remove(session);
    }

}
