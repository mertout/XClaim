package io.github.mertout.listeners;

import io.github.mertout.Claim;
import io.github.mertout.utils.UpdateChecker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent e) {
        if (e.getPlayer().isOp() &&
                Claim.getInstance().getConfig().getBoolean("settings.update-checker"))
            (new UpdateChecker(98880)).getVersion(version -> {
                if (!Claim.getInstance().getDescription().getVersion().equals(version))
                    e.getPlayer().sendMessage("§a[XClaim] There is a new update available.");
            });
    }
}
