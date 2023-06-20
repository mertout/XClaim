package io.github.mertout.gui;

import io.github.mertout.Claim;
import io.github.mertout.filemanager.files.MenusFile;
import io.github.mertout.utils.HexColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiCreator {

    public GuiCreator(Player p, GuiType guiType) {
        ConfigurationSection sect = MenusFile.get(guiType).getConfigurationSection("gui.items");
        if (sect == null) {
            System.out.println("ERROR! CREATING GUI " + guiType);
            return;
        }
        if (guiType == GuiType.CLAIMMANAGEMENT) {
            Inventory inv;
            String type = MenusFile.get(guiType).getString("gui.type");
            inv = (type != "chest" ? Bukkit.createInventory(null, InventoryType.valueOf(MenusFile.get(guiType).getString("gui.type").toUpperCase()), HexColor.hex(MenusFile.get(guiType).getString("gui.title"))) : Bukkit.createInventory(null, (MenusFile.get(guiType).getInt("gui.size") * 9), HexColor.hex(MenusFile.get(guiType).getString("gui.title"))));
            for (final String str : sect.getKeys(false)) {
                String mat = MenusFile.get(guiType).getString("gui.items." + str + ".material");
                List<Integer> slots = new ArrayList<>();
                if (MenusFile.get(guiType).isSet("gui.items." + str + ".slot")) {
                    slots.add(MenusFile.get(guiType).getInt("gui.items." + str + ".slot"));
                }
                else if (MenusFile.get(guiType).isSet("gui.items." + str + ".slots")) {
                    for (Object slot : MenusFile.get(guiType).getList("gui.items." + str + ".slots")) {
                        if (String.valueOf(slot).contains("-")) {
                            String[] range = String.valueOf(slot).split("-");
                            int start = Integer.parseInt(range[0]);
                            int end = Integer.parseInt(range[1]);
                            for (int i = start; i <= end; i++) {
                                slots.add(i);
                            }
                        } else {
                            slots.add(Integer.parseInt(slot.toString()));
                        }
                    }
                }
                final ItemStack is = new ItemStack(Material.matchMaterial(mat));
                final ItemMeta im = is.getItemMeta();
                if (MenusFile.get(guiType).isSet("gui.items." + str + ".display-name")) {
                    im.setDisplayName(HexColor.hex(MenusFile.get(guiType).getString("gui.items." + str + ".display-name")));
                }
                final ArrayList<String> lore = new ArrayList<>();
                for (final String str2 : MenusFile.get(guiType).getStringList("gui.items." + str + ".lore")) {
                    lore.add(HexColor.hex(str2));
                }
                im.setLore(lore);
                is.setItemMeta(im);
                for (int num : slots) {
                    inv.setItem(num, is);
                }
                p.openInventory(inv);
            }
        }
        else if (guiType == GuiType.MEMBERS) {
            Claim.getInstance().getMemberManager().openMemberPage(p, 1, 0);
        }
    }
}

