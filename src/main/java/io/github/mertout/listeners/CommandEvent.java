package io.github.mertout.listeners;

import io.github.mertout.Claim;
import io.github.mertout.core.ClaimManager;
import io.github.mertout.filemanager.MessagesFile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandEvent extends ClaimManager implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (super.hasClaimAtLoc(e.getPlayer().getLocation(),e.getPlayer())) {
            String cmd = e.getMessage().replace("/", "");
            for (String str : Claim.getInstance().getConfig().getStringList("settings.disabled-commands")) {
                if (cmd.equals(str)) {
                    e.getPlayer().sendMessage(MessagesFile.convertString("messages.disabled-commands-in-claim").replace("{command}", cmd));
                    e.setCancelled(true);
                }
            }
        }
    }
}
