package io.github.mertout.listeners;

import io.github.mertout.Claim;
import io.github.mertout.addons.XMaterial;
import io.github.mertout.core.ClaimManager;
import io.github.mertout.core.data.DataHandler;
import io.github.mertout.gui.GuiManager;
import io.github.mertout.addons.HexColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BlockClickListener extends ClaimManager implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (super.hasClaimAtLoc(e.getClickedBlock().getLocation(), e.getPlayer())) {
                e.setCancelled(true);
            }
            else {
                DataHandler data = super.getChunk(e.getClickedBlock().getLocation());
                if (data != null) {
                    if (data.getBlockLocation().equals(e.getClickedBlock().getLocation())) {
                        if (data.getOwner().equals(e.getPlayer().getName())) {
                            GuiManager inv = new GuiManager();
                            ConfigurationSection sect = Claim.getInstance().getConfig().getConfigurationSection("settings.claim-gui.items");
                            if (sect != null){
                                for (String str : sect.getKeys(false)) {
                                    String mat = Claim.getInstance().getConfig().getString("settings.claim-gui.items." + str + ".Material");
                                    Integer slot = Claim.getInstance().getConfig().getInt("settings.claim-gui.items." + str + ".Slot");
                                    ItemStack is = XMaterial.valueOf(mat).parseItem();
                                    ItemMeta im = is.getItemMeta();
                                    if (Claim.getInstance().getConfig().isSet("settings.claim-gui.items." + str + ".Display-Name")) {
                                        String lx = Claim.getInstance().getConfig().getString("settings.claim-gui.items." + str + ".Display-Name");
                                        lx = HexColor.hex(lx);
                                        im.setDisplayName(lx);
                                    }
                                    is.setItemMeta(im);
                                    inv.addItem(is, slot);
                                }
                            inv.openCreatedInventory(e.getPlayer());
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
}