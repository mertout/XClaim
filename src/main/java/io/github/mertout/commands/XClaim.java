package io.github.mertout.commands;

import de.tr7zw.changeme.nbtapi.NBTItem;
import io.github.mertout.utils.HexColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import io.github.mertout.Claim;
import io.github.mertout.filemanager.MessagesFile;
import org.bukkit.command.Command;
import org.jetbrains.annotations.NotNull;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class XClaim implements CommandExecutor {

    public boolean onCommand(@NotNull final CommandSender cs, @NotNull final Command cmd, @NotNull final String s, @NotNull final String[] strings) {
        if (strings.length <= 0) {
            cs.sendMessage(MessagesFile.convertString("messages.arg-error"));
            return true;
            }
        if (strings[0].equals("reload")) {
            if (!cs.hasPermission("xclaim.reload")) {
                cs.sendMessage(MessagesFile.convertString("messages.doesnt-permission"));
                return true;
            }
            Claim.getInstance().reloadConfig();
            MessagesFile.reloadMessages();
            cs.sendMessage(MessagesFile.convertString("messages.reloaded-files"));
            return false;
        }
        else if (strings[0].equals("give")) {
            if (cs instanceof Player) {
                final Player p = (Player)cs;
                if (!p.hasPermission("xclaim.give")) {
                    p.sendMessage(MessagesFile.convertString("messages.doesnt-permission"));
                    return true;
                }
            }
            if (strings.length != 2) {
                cs.sendMessage(MessagesFile.convertString("messages.arg-error"));
                return true;
            }
            if (Bukkit.getPlayer(strings[1]) == null) {
                cs.sendMessage(MessagesFile.convertString("messages.player-is-not-online").replace("{player}", strings[1]));
                return true;
            }
            final Player p2 = Bukkit.getPlayer(strings[1]);
            ItemStack is = new ItemStack(Material.matchMaterial(Claim.getInstance().getConfig().getString("settings.claim-block.material")));
            final ItemMeta im = is.getItemMeta();
            im.setDisplayName(MessagesFile.convertStringCFG("settings.claim-block.display-name"));
            final ArrayList<String> lore = new ArrayList<String>();
            for (final String str : Claim.getInstance().getConfig().getStringList("settings.claim-block.lore")) {
                lore.add(HexColor.hex(str).replaceAll("&", "§"));
            }
            im.setLore(lore);
            is.setItemMeta(im);
            NBTItem nbti = new NBTItem(is);
            nbti.setBoolean("claimblock", true);
            is = nbti.getItem();
            p2.getInventory().addItem(is);
            return false;
        }
        else {
            cs.sendMessage(MessagesFile.convertString("messages.arg-error"));
        }
        return false;
    }
}
