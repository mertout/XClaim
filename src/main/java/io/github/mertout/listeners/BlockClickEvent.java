// 
// Decompiled by Procyon v0.5.36
// 

package io.github.mertout.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;
import org.bukkit.configuration.ConfigurationSection;
import io.github.mertout.core.data.DataHandler;
import java.util.ArrayList;
import io.github.mertout.utils.HexColor;
import io.github.mertout.Claim;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.Listener;
import io.github.mertout.core.ClaimManager;

public class BlockClickEvent extends ClaimManager implements Listener
{
    @EventHandler
    public void onClick(final PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (super.hasClaimAtLoc(e.getClickedBlock().getLocation(), e.getPlayer())) {
                e.setCancelled(true);
            }
            else {
                final DataHandler data = super.getChunk(e.getClickedBlock().getLocation());
                if (data != null && data.getBlockLocation().equals(e.getClickedBlock().getLocation())) {
                    if (data.getOwner().equals(e.getPlayer().getName())) {
                        final ConfigurationSection sect = Claim.getInstance().getConfig().getConfigurationSection("gui.claim-gui.items");
                        final Inventory inv;
                        if (!Claim.getInstance().getConfig().getString("gui.claim-gui.gui-type").equalsIgnoreCase("chest")) {
                            inv = Bukkit.createInventory(null, InventoryType.valueOf(Claim.getInstance().getConfig().getString("gui.claim-gui.gui-type").toUpperCase()), HexColor.hex(Claim.getInstance().getConfig().getString("gui.claim-gui.gui-title").replaceAll("&", "§")));
                        }
                        else {
                            inv = Bukkit.createInventory(null, InventoryType.CHEST, HexColor.hex(Claim.getInstance().getConfig().getString("gui.claim-gui.gui-title").replaceAll("&", "§")));
                        }
                        if (sect != null) {
                            for (final String str : sect.getKeys(false)) {
                                final String mat = Claim.getInstance().getConfig().getString("gui.claim-gui.items." + str + ".material");
                                final int slot = Claim.getInstance().getConfig().getInt("gui.claim-gui.items." + str + ".slot");
                                final ItemStack is = new ItemStack(Material.matchMaterial(mat));
                                final ItemMeta im = is.getItemMeta();
                                if (Claim.getInstance().getConfig().isSet("gui.claim-gui.items." + str + ".display-name")) {
                                    im.setDisplayName(HexColor.hex(Claim.getInstance().getConfig().getString("gui.claim-gui.items." + str + ".display-name").replaceAll("&", "§")));
                                }
                                final ArrayList<String> lore = new ArrayList<String>();
                                for (final String str2 : Claim.getInstance().getConfig().getStringList("gui.claim-gui.items." + str + ".lore")) {
                                    lore.add(HexColor.hex(str2).replaceAll("&", "§"));
                                }
                                im.setLore(lore);
                                is.setItemMeta(im);
                                inv.setItem(slot, is);
                            }
                            e.getPlayer().openInventory(inv);
                        }
                    }
                    else {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
