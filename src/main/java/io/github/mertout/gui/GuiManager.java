package io.github.mertout.gui;

import io.github.mertout.Claim;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GuiManager {

    public Inventory inv;
    public String title;
    public String type;
    public Integer size;

    public GuiManager() {
        title = Claim.getInstance().getConfig().getString("settings.claim-gui.gui-title");
        type = Claim.getInstance().getConfig().getString("settings.claim-gui.gui-type").toLowerCase();
        size = Claim.getInstance().getConfig().getInt("settings.claim-gui.gui-size") * 9;
        if (!type.equals("chest")) {
            inv = Bukkit.createInventory(null, InventoryType.valueOf(type.toUpperCase()), title.replaceAll("&", "§"));
        }
        else {
             inv = Bukkit.createInventory(null, InventoryType.CHEST, title.replaceAll("&", "§"));
        }
    }
    public void addItem(@NotNull ItemStack is, @NotNull Integer slot) {
        inv.setItem(slot, is);
    }
    public void openCreatedInventory(@NotNull Player p) {
        p.openInventory(inv);
    }
}
