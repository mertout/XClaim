package io.github.mertout.listeners;

import io.github.mertout.Claim;
import io.github.mertout.core.ClaimManager;
import io.github.mertout.core.data.DataHandler;
import io.github.mertout.filemanager.MessagesFile;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent extends ClaimManager implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (Claim.getInstance().getMembers().contains(e.getPlayer())) {
            if (super.hasClaim(e.getPlayer())) {
                e.setCancelled(true);
                if (!e.getMessage().equals("-")) {
                    OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(e.getMessage());
                    if (p.isOnline()) {
                        if (!e.getMessage().contains(e.getPlayer().getName())) {
                            DataHandler data = super.PlayerToClaim(e.getPlayer());
                            data.getMembers().add(e.getMessage());
                            e.getPlayer().sendMessage(MessagesFile.convertString("messages.member-added")
                                    .replace("{player}", e.getMessage()));
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
                    e.getPlayer().sendMessage(MessagesFile.convertString("messages.friends-add-canceled"));
                }
                Claim.getInstance().getMembers().remove(e.getPlayer());
            }
        }
    }
}
