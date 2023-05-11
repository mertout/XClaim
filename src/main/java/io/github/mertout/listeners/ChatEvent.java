package io.github.mertout.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import io.github.mertout.core.data.DataHandler;
import org.bukkit.OfflinePlayer;
import io.github.mertout.filemanager.files.MessagesFile;
import org.bukkit.Bukkit;
import io.github.mertout.Claim;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.Listener;
import io.github.mertout.core.ClaimManager;

public class ChatEvent extends ClaimManager implements Listener
{
    @EventHandler
    public void onChat(final AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        String message = e.getMessage();
        if (Claim.getInstance().getAddList().contains(player) && super.hasClaim(player)) {
            e.setCancelled(true);
            if (message.equals("-")) {
                Claim.getInstance().getAddList().remove(player);
                player.sendMessage(MessagesFile.convertString("messages.member-add-canceled"));
                return;
            }
            if (!Bukkit.getPlayer(message).isOnline()) {
                player.sendMessage(MessagesFile.convertString("messages.player-not-found"));
                return;
            }
            Player p2 = Bukkit.getServer().getPlayer(message);
            if (message.contains(player.getName())) {
                player.sendMessage(MessagesFile.convertString("messages.add-error"));
            }
            final DataHandler data = super.getPlayerClaim(player);
            Claim.getInstance().getAddList().remove(player);
            if (!Claim.getInstance().getMemberManager().canAddAnyMember(player)) {
                player.sendMessage(MessagesFile.convertString("messages.claim-is-full")
                        .replace("{max}", Claim.getInstance().getMemberManager().getMaxMemberSize(player) + ""));
                return;
            }
            if (Claim.getInstance().getClaimManager().getPlayerClaim(player).getMembers().contains(message)) {
                player.sendMessage(MessagesFile.convertString("messages.member-already-in-claim")
                        .replace("{target}", message));
                return;
            }
            Claim.getInstance().getMemberManager().addMemberToClaim(player, data, p2);
            player.sendMessage(MessagesFile.convertString("messages.member-added")
                    .replace("{player}", message));
        }
    }
}
