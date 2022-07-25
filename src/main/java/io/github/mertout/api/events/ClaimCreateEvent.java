package io.github.mertout.api.events;

import org.jetbrains.annotations.NotNull;
import org.bukkit.event.HandlerList;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class ClaimCreateEvent extends Event implements Cancellable
{
    public Player p;
    public static final HandlerList handlers;
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
    
    public HandlerList getHandlers() {
        return ClaimCreateEvent.handlers;
    }
    
    public Player getPlayer() {
        return this.p;
    }
    
    static {
        handlers = new HandlerList();
    }
}
