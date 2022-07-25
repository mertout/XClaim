package io.github.mertout.listeners;

import de.tr7zw.changeme.nbtapi.NBTItem;
import io.github.mertout.api.events.ClaimBlockMoveEvent;
import io.github.mertout.api.events.ClaimDayRenewEvent;
import io.github.mertout.core.timer.MoveTimer;
import io.github.mertout.utils.HexColor;
import io.github.mertout.core.data.DataHandler;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.bukkit.event.EventHandler;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import io.github.mertout.filemanager.MessagesFile;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import io.github.mertout.Claim;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.Listener;
import io.github.mertout.core.ClaimManager;

import java.util.ArrayList;

public class ClickEvent extends ClaimManager implements Listener
{
    @EventHandler
    public void onClick(final InventoryClickEvent e) {
        if (e.getWhoClicked().getOpenInventory().getTitle().equals(Claim.getInstance().getConfig().getString("gui.management-gui.member-list.gui-title").replaceAll("&", "§"))) {
            e.setCancelled(true);
        }
        if (e.getWhoClicked().getOpenInventory().getTitle().equals(Claim.getInstance().getConfig().getString("gui.management-gui.member-remove.gui-title").replaceAll("&", "§"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
                if (new NBTItem(e.getCurrentItem()).getString("owner") != null) {
                    NBTItem nbti = new NBTItem(e.getCurrentItem());
                    memberremove((Player) e.getWhoClicked(),nbti.getString("owner"));
                }
            }
        }
        if (e.getWhoClicked().getOpenInventory().getTitle().equals(HexColor.hex(Claim.getInstance().getConfig().getString("gui.claim-gui.gui-title").replaceAll("&", "§")))) {
            e.setCancelled(true);
            e.getWhoClicked().getOpenInventory().close();
            menuClick((Player) e.getWhoClicked(), e.getSlot());

        }
    }
    private void menuClick(@NotNull Player p, int slot) {
        final ConfigurationSection sect = Claim.getInstance().getConfig().getConfigurationSection("gui.claim-gui.items");
        if (sect != null)
            for (final String str : sect.getKeys(false))
                if (Claim.getInstance().getConfig().getInt("gui.claim-gui.items." + str + ".slot") == slot && Claim.getInstance().getConfig().isSet("gui.claim-gui.items." + str + ".action")) {
                    final String action = Claim.getInstance().getConfig().getString("gui.claim-gui.items." + str + ".action");
                    final String sw = action;
                    switch (sw) {
                        case "[Member-List]":
                            this.memberList(p);
                            break;
                        case "[Member-Add]":
                            Claim.getInstance().getMembers().add(p);
                            p.sendMessage(MessagesFile.convertString("messages.member-adding"));
                            break;
                        case "[Member-Remove]":
                            this.memberRemove(p);
                            break;
                        case "[Renew-Day]":
                            this.renewDay(p);
                            break;
                        case "[Delete-Claim]":
                            super.deleteClaims(p);
                            break;
                        case "[Move-Claim-Block]":
                            this.moveClaimBlock(p);
                            break;
            }
        }
    }
    
    private void memberList(@NotNull final Player p) {
        final Inventory inv = Bukkit.createInventory(null, 54, HexColor.hex(Claim.getInstance().getConfig().getString("gui.management-gui.member-list.gui-title").replaceAll("&", "§")));
        final DataHandler data = super.PlayerToClaim(p);
        if (data.getMembers().size() > 0) {
            if (!Claim.getInstance().getConfig().getString("gui.management-gui.member-list.material").equals("TARGETED_HEAD")) {
                for (final String plist : data.getMembers()) {
                    ItemStack stack = new ItemStack(Material.matchMaterial(Claim.getInstance().getConfig().getString("gui.management-gui.member-list.material")));
                    ItemMeta meta = stack.getItemMeta();
                    meta.setDisplayName(HexColor.hex(Claim.getInstance().getConfig().getString("gui.management-gui.member-list.display-name").replace("{player}", plist).replaceAll("&", "§")));
                    ArrayList<String> lore = new ArrayList<String>();
                    for (String str : Claim.getInstance().getConfig().getStringList("gui.management-gui.member-list.lore")) {
                        lore.add(MessagesFile.convertStringCFG(str).replace("{status}", checkStatus(plist)));
                    }
                    meta.setLore(lore);
                    stack.setItemMeta(meta);
                    inv.addItem(stack);
                }
            }
            else {
                for (final String plist : data.getMembers()) {
                    final ItemStack stack = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short)3);
                    final SkullMeta meta = (SkullMeta)stack.getItemMeta();
                    meta.setOwner(plist);
                    meta.setDisplayName(HexColor.hex(Claim.getInstance().getConfig().getString("gui.management-gui.member-list.display-name").replace("{player}", plist).replaceAll("&", "§")));
                    ArrayList<String> lore = new ArrayList<String>();
                    for (String str : Claim.getInstance().getConfig().getStringList("gui.management-gui.member-list.lore")) {
                        lore.add(MessagesFile.convertStringCFG(str).replace("{status}", checkStatus(plist)));
                    }
                    meta.setLore(lore);
                    stack.setItemMeta(meta);
                    inv.addItem(stack);
                }
            }
            p.openInventory(inv);
        }
        else {
            p.sendMessage(MessagesFile.convertString("messages.no-player-in-claim"));
        }
    }
    
    private void memberRemove(@NotNull final Player p) {
        final Inventory inv = Bukkit.createInventory(null, 54, HexColor.hex(Claim.getInstance().getConfig().getString("gui.management-gui.member-remove.gui-title").replaceAll("&", "§")));
        final DataHandler data = super.PlayerToClaim(p);
        if (data.getMembers().size() > 0) {
            if (!Claim.getInstance().getConfig().getString("gui.management-gui.member-remove.material").equals("TARGETED_HEAD")) {
                for (final String plist : data.getMembers()) {
                        ItemStack stack = new ItemStack(Material.matchMaterial(Claim.getInstance().getConfig().getString("gui.management-gui.member-remove.material")));
                        ItemMeta meta = stack.getItemMeta();
                        meta.setDisplayName(HexColor.hex(Claim.getInstance().getConfig().getString("gui.management-gui.member-remove.display-name").replace("{player}", plist).replaceAll("&", "§")));
                        ArrayList<String> lore = new ArrayList<String>();
                        for (String str : Claim.getInstance().getConfig().getStringList("gui.management-gui.member-remove.lore")) {
                            lore.add(MessagesFile.convertStringCFG(str).replace("{status}", checkStatus(plist)));
                        }
                        meta.setLore(lore);
                        stack.setItemMeta(meta);
                        NBTItem nbti = new NBTItem(stack);
                        nbti.setString("owner", plist);
                        stack = nbti.getItem();
                        inv.addItem(stack);
                    }
                }
           else {
                for (final String plist : data.getMembers()) {
                    ItemStack stack = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short)3);
                    SkullMeta meta = (SkullMeta)stack.getItemMeta();
                    meta.setOwner(plist);
                    meta.setDisplayName(HexColor.hex(Claim.getInstance().getConfig().getString("gui.management-gui.member-remove.display-name").replace("{player}", plist).replaceAll("&", "§")));
                    ArrayList<String> lore = new ArrayList<String>();
                    for (String str : Claim.getInstance().getConfig().getStringList("gui.management-gui.member-remove.lore")) {
                        lore.add(MessagesFile.convertStringCFG(str).replace("{status}", checkStatus(plist)));
                    }
                    meta.setLore(lore);
                    stack.setItemMeta(meta);
                    NBTItem nbti = new NBTItem(stack);
                    nbti.setString("owner", plist);
                    stack = nbti.getItem();
                    inv.addItem(stack);
                    }
            }
            p.openInventory(inv);
        }
        else {
            p.sendMessage(MessagesFile.convertString("messages.no-player-in-claim"));
        }
    }
    
    private void memberremove(@NotNull final Player p, @NotNull final String owner) {
        final DataHandler data = super.PlayerToClaim(p);
        if (data.getMembers().contains(owner)) {
            data.getMembers().remove(owner);
            p.getOpenInventory().close();
            p.sendMessage(MessagesFile.convertString("messages.kicked-claim").replace("{player}", owner));
        }
    }
    
    private void renewDay(@NotNull final Player p) {
        if (super.hasClaim(p)) {
            if (Claim.getInstance().getEconomy().getBalance(p) >= Claim.getInstance().getConfig().getInt("settings.renew-day-cost")) {
                final DataHandler data = super.PlayerToClaim(p);
                final ClaimDayRenewEvent event = new ClaimDayRenewEvent(p, data);
                Bukkit.getServer().getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    Claim.getInstance().getEconomy().withdrawPlayer(p, Claim.getInstance().getConfig().getInt("settings.renew-day-cost"));
                    data.addDay(30);
                    p.sendMessage(MessagesFile.convertString("messages.renewed-day").replace("{day}", data.getDay() + "").replace("{hour}", data.getHour() + "").replace("{minute}", data.getMinutes() + "").replace("{second}", data.getSeconds() + ""));
                }
            }
            else {
                p.sendMessage(MessagesFile.convertString("messages.dont-have-money").replace("{money}", Claim.getInstance().getConfig().getString("settings.renew-day-cost")));
            }
        }
    }
    public String checkStatus(String params) {
        if (Bukkit.getPlayer(params) != null) {
            return MessagesFile.convertString("messages.status.online");
        }
        else {
            return MessagesFile.convertString("messages.status.offline");
        }
    }
    public void moveClaimBlock(final Player p) {
        if (super.hasClaim(p)) {
            final DataHandler data = super.PlayerToClaim(p);
            if (p.getLocation().getChunk().toString().equals(data.getChunk())) {
                if (!MoveTimer.getMoveTask().containsKey(p)) {
                    final ClaimBlockMoveEvent event = new ClaimBlockMoveEvent(p, data);
                    Bukkit.getServer().getPluginManager().callEvent(event);
                    if (!event.isCancelled()) {
                        Claim.getInstance().getHologramCore().moveHologram(data, p.getLocation().getBlock().getLocation());
                        final Location loc = data.getBlockLocation();
                        loc.getBlock().setType(Material.AIR);
                        data.setBlockLocation(p.getLocation().getBlock().getLocation());
                        p.getLocation().getBlock().setType(Material.matchMaterial(Claim.getInstance().getConfig().getString("settings.claim-block.material")));
                        Claim.getInstance().getMoveTimer().addMoveCooldown(p);
                        p.sendMessage(MessagesFile.convertString("messages.moved-claim-block"));
                    }
                }
                else {
                    int hour = 0;
                    int minute = 0;
                    int second;
                    for (second = (int) MoveTimer.getMoveTask().get(p); second > 3600; second -= 3600, ++hour) {}
                    while (second > 60) {
                        second -= 60;
                        ++minute;
                    }
                    p.sendMessage(MessagesFile.convertString("messages.move-claim-block-cooldown").replace("{hour}", hour + "").replace("{minute}", minute + "").replace("{second}", second + ""));
                }
            }
            else {
                p.sendMessage(MessagesFile.convertString("messages.move-claim-block-error"));
            }
        }
    }
}
