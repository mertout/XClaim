package io.github.mertout.api.events;

import org.jetbrains.annotations.NotNull;
import org.bukkit.event.HandlerList;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class ClaimCreateEvent extends Event implements Cancellable
{
    public Player p;
    public static final HandlerList handlers = new HandlerList();
    public boolean cancelled;
    
    public ClaimCreateEvent(@NotNull final Player x) {
        this.p = x;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean arg0) {
        this.cancelled = arg0;
    }

    public Player getPlayer() {
        return this.p;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
