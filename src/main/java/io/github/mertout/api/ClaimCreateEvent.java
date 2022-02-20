package io.github.mertout.api;

import io.github.mertout.core.data.DataHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ClaimCreateEvent extends Event implements Cancellable {

    public Player p;
    public static final HandlerList handlers = new HandlerList();
    public boolean cancelled;

    public ClaimCreateEvent(@NotNull Player x) {
        this.p = x;
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

    public Player getPlayer() {
        return p;
    }
}