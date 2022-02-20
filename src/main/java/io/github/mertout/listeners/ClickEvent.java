package io.github.mertout.listeners;

import de.tr7zw.changeme.nbtapi.NBTItem;
import io.github.mertout.Claim;
import io.github.mertout.core.ClaimManager;
import io.github.mertout.core.data.DataHandler;
import io.github.mertout.filemanager.MessagesFile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

public class ClickEvent extends ClaimManager implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getWhoClicked().getOpenInventory().getTitle().equals(Claim.getInstance().getConfig().getString("settings.gui.member-list.gui-title").replaceAll("&", "§"))) {
            e.setCancelled(true);
        }
        if (e.getWhoClicked().getOpenInventory().getTitle().equals(Claim.getInstance().getConfig().getString("settings.gui.member-remove.gui-title").replaceAll("&", "§"))) {
            e.setCancelled(true);
            ItemStack is = e.getCurrentItem();
            NBTItem nbti = new NBTItem(is);
            memberremove((Player) e.getWhoClicked(),nbti.getString("owner"));
        }
        if (e.getWhoClicked().getOpenInventory().getTitle().equals(Claim.getInstance().getConfig().getString("settings.claim-gui.gui-title").replaceAll("&", "§"))) {
            e.setCancelled(true);
            e.getWhoClicked().getOpenInventory().close();
            ConfigurationSection sect = Claim.getInstance().getConfig().getConfigurationSection("settings.claim-gui.items");
            if (sect != null){
                for (String str : sect.getKeys(false)) {
                    if (Claim.getInstance().getConfig().getInt("settings.claim-gui.items." + str + ".Slot") == e.getSlot()) {
                        if (Claim.getInstance().getConfig().isSet("settings.claim-gui.items." + str + ".Action")) {
                            String action = Claim.getInstance().getConfig().getString("settings.claim-gui.items." + str + ".Action");
                            Player p = (Player) e.getWhoClicked();
                            switch (action) {
                                case "[Member-List]":
                                    memberlist(p);
                                    break;
                                case "[Member-Add]":
                                    Claim.getInstance().getMembers().add(p);
                                    p.sendMessage(MessagesFile.convertString("messages.member-adding"));
                                    break;
                                case "[Member-Remove]":
                                    memberremovegui(p);
                                    break;
                                case "[Renew-Day]":
                                    renewday(p);
                                    break;
                                case "[Delete-Claim]":
                                    super.deleteClaims(p);
                                    break;
                                case "[Move-Claim-Block]":
                                    super.moveClaimBlock(p);
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }
    private void memberlist(@NotNull Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, Claim.getInstance().getConfig().getString("settings.gui.member-list.gui-title").replaceAll("&", "§"));
        DataHandler data = super.PlayerToClaim(p);
        if (data.getMembers().size() > 0) {
            for (String plist : data.getMembers()) {
                ItemStack stack = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (byte) 3);
                SkullMeta meta = (SkullMeta) stack.getItemMeta();
                meta.setOwner(plist);
                meta.setDisplayName(Claim.getInstance().getConfig().getString("settings.gui.member-list.item-name")
                        .replace("{player}", plist)
                        .replaceAll("&", "§"));
                stack.setItemMeta(meta);
                inv.addItem(stack);
            }
            p.openInventory(inv);
        }
        else {
            p.sendMessage(MessagesFile.convertString("messages.no-player-in-claim"));
        }
    }
    private void memberremovegui(@NotNull Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, Claim.getInstance().getConfig().getString("settings.gui.member-remove.gui-title").replaceAll("&", "§"));
        DataHandler data = super.PlayerToClaim(p);
        if (data.getMembers().size() > 0) {
            for (String plist : data.getMembers()) {
                ItemStack stack = new ItemStack(Material.STONE);
                NBTItem nbti = new NBTItem(stack);
                nbti.setString("owner", plist);
                stack = nbti.getItem();
                ItemMeta meta = stack.getItemMeta();
                meta.setDisplayName(Claim.getInstance().getConfig().getString("settings.gui.member-remove.item-name")
                        .replace("{player}", plist)
                        .replaceAll("&", "§"));
                stack.setItemMeta(meta);
                inv.addItem(stack);
            }
            p.openInventory(inv);
        }
        else {
            p.sendMessage(MessagesFile.convertString("messages.no-player-in-claim"));
        }
    }
    private void memberremove(@NotNull Player p, @NotNull String owner) {
        DataHandler data = super.PlayerToClaim(p);
        Bukkit.broadcastMessage("check: " + owner);
        if (data.getMembers().contains(owner)) {
            data.getMembers().remove(owner);
            p.getOpenInventory().close();
            p.sendMessage(MessagesFile.convertString("messages.kicked-claim").replace("{player}", owner));
        }
    }
    private void renewday(@NotNull Player p) {
        if (super.hasClaim(p)) {
            if (Claim.getInstance().getEconomy().getBalance(p) >= Claim.getInstance().getConfig().getInt("settings.renew-day-cost")) {
                Claim.getInstance().getEconomy().withdrawPlayer(p, Claim.getInstance().getConfig().getInt("settings.renew-day-cost"));
                DataHandler data = super.PlayerToClaim(p);
                data.addDay(30);
                p.sendMessage(MessagesFile.convertString("messages.renewed-day")
                        .replace("{day}", data.getDay() + "")
                        .replace("{hour}", data.getHour() + "")
                        .replace("{minute}", data.getMinutes() + "")
                        .replace("{second}", data.getSeconds() + ""));

            }
            else {
                p.sendMessage(MessagesFile.convertString("messages.dont-have-money"));
            }
        }
    }
}
