package io.github.mertout.api;

import org.bukkit.event.Cancellable;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ClaimCreateEvent extends Event implements Cancellable {

    public static final HandlerList handlers = new HandlerList();
    public boolean cancelled;

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
}