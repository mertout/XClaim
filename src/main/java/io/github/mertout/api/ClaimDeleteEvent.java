package io.github.mertout.api;

import io.github.mertout.core.data.DataHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ClaimDeleteEvent extends Event implements Cancellable {

    public DataHandler data;
    public Player p;
    public static final HandlerList handlers = new HandlerList();
    public boolean cancelled;

    public ClaimDeleteEvent(@NotNull DataHandler x, Player y) {
        this.data = x;
        this.p = y;
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

    public Player getPlayer() {
        return p;
    }
}