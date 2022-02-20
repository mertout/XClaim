package io.github.mertout.commands;

import io.github.mertout.Claim;
import io.github.mertout.addons.XMaterial;
import io.github.mertout.filemanager.MessagesFile;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class XClaim implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String s, @NotNull String[] strings) {
        if (strings.length > 0) {
            if (strings[0].equals("give")) {
                if (cs instanceof Player) {
                    Player p = (Player) cs;
                    if (!p.hasPermission("xclaim.give")) {
                        p.sendMessage(MessagesFile.convertString("messages.doesnt-permission"));
                        return true;
                    }
                }
                if (strings.length != 2) {
                    cs.sendMessage( MessagesFile.convertString("messages.arg-error"));
                    return true;
                }
                Player p2 = Bukkit.getPlayer(strings[1]);
                ItemStack is = XMaterial.BEDROCK.parseItem();
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(Claim.getInstance().getConfig().getString("settings.claim-block-name").replaceAll("&", "§"));
                is.setItemMeta(im);
                p2.getInventory().addItem(is);
                return false;
            }
            else if (strings[0].equals("reload")) {
                if (!cs.hasPermission("xclaim.reload")) {
                    cs.sendMessage(MessagesFile.convertString("messages.doesnt-permission"));
                    return true;
                }
                Claim.getInstance().reloadConfig();
                MessagesFile.reloadMessages();
                cs.sendMessage(MessagesFile.convertString("messages.reloaded-files"));
            }
        }
        else {
            cs.sendMessage(MessagesFile.convertString("messages.arg-error"));
        }
        return false;
    }
}
