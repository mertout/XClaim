package io.github.mertout.api;

import io.github.mertout.core.data.DataHandler;
import org.bukkit.event.Cancellable;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ClaimExpireEvent extends Event implements Cancellable {

    public DataHandler data;
    public static final HandlerList handlers = new HandlerList();
    public boolean cancelled;

    public ClaimExpireEvent(@NotNull DataHandler data) {
        this.data = data;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean arg0) {
        cancelled = arg0;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public DataHandler getData() {
        return data;
    }
}