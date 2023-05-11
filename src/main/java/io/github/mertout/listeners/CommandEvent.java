package io.github.mertout.listeners;

import org.bukkit.event.EventHandler;
import io.github.mertout.filemanager.files.MessagesFile;
import io.github.mertout.Claim;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.Listener;
import io.github.mertout.core.ClaimManager;

public class CommandEvent extends ClaimManager implements Listener
{
    @EventHandler
    public void onCommand(final PlayerCommandPreprocessEvent e) {
        if (super.hasClaimAtLocation(e.getPlayer().getLocation(), e.getPlayer())) {
            final String cmd = e.getMessage().replace("/", "");
            for (final String str : Claim.getInstance().getConfig().getStringList("settings.disabled-commands")) {
                if (cmd.equals(str)) {
                    e.getPlayer().sendMessage(MessagesFile.convertString("messages.disabled-commands-in-claim").replace("{command}", cmd));
                    e.setCancelled(true);
                }
            }
        }
    }
}
