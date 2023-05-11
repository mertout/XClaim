package io.github.mertout.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import io.github.mertout.core.ClaimManager;

public class MinecartEvent extends ClaimManager implements Listener
{
    @EventHandler
    public void onMinecartEnter(final VehicleEnterEvent e) {
        if (super.getChunkClaim(e.getVehicle().getLocation()) != null && super.hasClaimAtLocation(e.getVehicle().getLocation(), (Player)e.getEntered())) { e.setCancelled(true); }
    }
}
