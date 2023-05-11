package io.github.mertout.listeners;

import de.tr7zw.changeme.nbtapi.NBTItem;
import io.github.mertout.filemanager.files.MenusFile;
import io.github.mertout.gui.GuiType;
import io.github.mertout.gui.backend.MenuClick;
import io.github.mertout.utils.ClaimUtils;
import io.github.mertout.utils.HexColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.Listener;
import io.github.mertout.core.ClaimManager;

public class ClickEvent extends ClaimManager implements Listener
{
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getWhoClicked().getOpenInventory().getTitle().equals(HexColor.hex(MenusFile.get(GuiType.CLAIMMANAGEMENT).getString("gui.title")))) {
            e.setCancelled(true);
            e.getWhoClicked().getOpenInventory().close();
            new MenuClick((Player) e.getWhoClicked(), e.getSlot(), GuiType.CLAIMMANAGEMENT);

        }
        if (e.getWhoClicked().getOpenInventory().getTitle().equals(HexColor.hex(MenusFile.get(GuiType.MEMBERS).getString("gui.title")))) {
            e.setCancelled(true);
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR && new NBTItem(e.getCurrentItem()).getString("owner") != null) {
                NBTItem nbti = new NBTItem(e.getCurrentItem());
                ClaimUtils.memberKick((Player) e.getWhoClicked(), nbti.getString("owner"));
            }
        }
    }
}
