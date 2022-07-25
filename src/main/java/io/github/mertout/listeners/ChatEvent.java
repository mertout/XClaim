package io.github.mertout.listeners;

import org.bukkit.event.EventHandler;
import io.github.mertout.core.data.DataHandler;
import org.bukkit.OfflinePlayer;
import io.github.mertout.filemanager.MessagesFile;
import org.bukkit.Bukkit;
import io.github.mertout.Claim;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.Listener;
import io.github.mertout.core.ClaimManager;

public class ChatEvent extends ClaimManager implements Listener
{
    @EventHandler
    public void onChat(final AsyncPlayerChatEvent e) {
        if (Claim.getInstance().getMembers().contains(e.getPlayer()) && super.hasClaim(e.getPlayer())) {
            e.setCancelled(true);
            if (!e.getMessage().equals("-")) {
                final OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(e.getMessage());
                if (p.isOnline()) {
                    if (!e.getMessage().contains(e.getPlayer().getName())) {
                        final DataHandler data = super.PlayerToClaim(e.getPlayer());
                        data.getMembers().add(e.getMessage());
                        e.getPlayer().sendMessage(MessagesFile.convertString("messages.member-added").replace("{player}", e.getMessage()));
                    }
                    else {
                        e.getPlayer().sendMessage(MessagesFile.convertString("messages.add-error"));
                    }
                }
                else {
                    e.getPlayer().sendMessage(MessagesFile.convertString("messages.player-not-found"));
                }
            }
            else {
                e.getPlayer().sendMessage(MessagesFile.convertString("messages.member-add-canceled"));
            }
            Claim.getInstance().getMembers().remove(e.getPlayer());
        }
    }
}
