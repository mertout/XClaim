package io.github.mertout.api.events;

import io.github.mertout.core.data.DataHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ClaimBlockMoveEvent extends Event implements Cancellable {

    public Player p;

    public DataHandler data;
    public static final HandlerList handlers;
    public boolean cancelled;

    public ClaimBlockMoveEvent(@NotNull final Player p, @NotNull final DataHandler data) {
        this.p = p;
        this.data = data;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(final boolean arg0) {
        this.cancelled = arg0;
    }

    public HandlerList getHandlers() {
        return ClaimBlockMoveEvent.handlers;
    }

    public DataHandler getData() {
        return this.data;
    }

    public Player getPlayer() {
        return p;
    }

    static {
        handlers = new HandlerList();
    }
}
