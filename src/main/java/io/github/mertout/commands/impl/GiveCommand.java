package io.github.mertout.commands.impl;

import de.tr7zw.changeme.nbtapi.NBTItem;
import io.github.mertout.Claim;
import io.github.mertout.commands.SubCommand;
import io.github.mertout.filemanager.files.MessagesFile;
import io.github.mertout.utils.HexColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GiveCommand extends SubCommand {

    @Override
    public String getName() {
        return "give";
    }

    @Override
    public String getSubName() {
        return "";
    }

    @Override
    public String getDescription() {
        return "Claim block give command!";
    }

    @Override
    public String getSyntax() {
        return "/xclaim give <player>";
    }

    @Override
    public String getPermission() {
        return "xclaim.give";
    }

    @Override
    public int getLength() {
        return 2;
    }

    @Override
    public void perform(CommandSender cs, String[] strings) {
        if (Bukkit.getPlayer(strings[1]) == null) {
            cs.sendMessage(MessagesFile.convertString("messages.player-is-not-online").replace("{player}", strings[1]));
            return;
        }
        Player target = Bukkit.getPlayer(strings[1]);
        ItemStack is = new ItemStack(Material.matchMaterial(Claim.getInstance().getConfig().getString("settings.claim-block.material")));
        final ItemMeta im = is.getItemMeta();
        im.setDisplayName(HexColor.hex(Claim.getInstance().getConfig().getString("settings.claim-block.display-name")));
        final ArrayList<String> lore = new ArrayList<String>();
        for (final String str : Claim.getInstance().getConfig().getStringList("settings.claim-block.lore")) {
            lore.add(HexColor.hex(str).replaceAll("&", "§"));
        }
        im.setLore(lore);
        is.setItemMeta(im);
        NBTItem nbti = new NBTItem(is);
        nbti.setBoolean("claimblock", true);
        is = nbti.getItem();
        target.getInventory().addItem(is);
    }
}
