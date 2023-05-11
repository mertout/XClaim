package io.github.mertout.api.events;

import io.github.mertout.core.data.DataHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ClaimMemberRemoveEvent extends Event implements Cancellable
{
    public DataHandler data;
    public Player p;
    public Player p2;
    public static final HandlerList handlers = new HandlerList();
    public boolean cancelled;

    public ClaimMemberRemoveEvent(@NotNull final DataHandler x, final Player y, final Player z) {
        this.data = x;
        this.p = y;
        this.p2 = z;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(final boolean arg0) {
        this.cancelled = arg0;
    }

    public DataHandler getData() {
        return this.data;
    }

    public Player getPlayer() {
        return this.p;
    }

    public Player getRemovedPlayer() {
        return this.p2;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
