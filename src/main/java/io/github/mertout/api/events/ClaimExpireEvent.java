package io.github.mertout.api.events;

import org.jetbrains.annotations.NotNull;
import org.bukkit.event.HandlerList;
import io.github.mertout.core.data.DataHandler;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class ClaimExpireEvent extends Event implements Cancellable
{
    public DataHandler data;
    public static final HandlerList handlers;
    public boolean cancelled;
    
    public ClaimExpireEvent(@NotNull final DataHandler data) {
        this.data = data;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean arg0) {
        this.cancelled = arg0;
    }
    
    public HandlerList getHandlers() {
        return ClaimExpireEvent.handlers;
    }
    
    public DataHandler getData() {
        return this.data;
    }
    
    static {
        handlers = new HandlerList();
    }
}
