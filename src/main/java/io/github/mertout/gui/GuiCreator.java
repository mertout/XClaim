package io.github.mertout.gui;

import de.tr7zw.changeme.nbtapi.NBTItem;
import io.github.mertout.Claim;
import io.github.mertout.core.data.DataHandler;
import io.github.mertout.filemanager.files.MenusFile;
import io.github.mertout.filemanager.files.MessagesFile;
import io.github.mertout.utils.ClaimUtils;
import io.github.mertout.utils.HexColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

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
            DataHandler data = Claim.getInstance().getClaimManager().getPlayerClaim(p);
            if (data == null || data.getMembers() == null || data.getMembers().size() < 1) {
                p.closeInventory();
                p.sendMessage(MessagesFile.convertString("messages.no-player-in-claim"));
                return;
            }
            Inventory inv = Bukkit.createInventory(null, (MenusFile.get(guiType).getInt("gui.size") * 9), HexColor.hex(MenusFile.get(guiType).getString("gui.title")));
            for (final String str : sect.getKeys(false)) {
                if (!str.equals("MEMBER")) {
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
                }
            }
            addMemberToGui(inv, data);
            p.openInventory(inv);
        }
    }
    public void addMemberToGui(Inventory inv, DataHandler data) {
        ItemStack stack = null;
        GuiType guiType = GuiType.MEMBERS;
        if (MenusFile.get(guiType).getString("gui.items.MEMBER").equals("TARGETED_HEAD")) {
            stack = new ItemStack(Material.matchMaterial(MenusFile.get(guiType).getString("gui.items.MEMBER.material")));
        }
        else {
            for (String member : data.getMembers()) {
                stack = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short)3);
                SkullMeta skullMetameta = (SkullMeta)stack.getItemMeta();
                skullMetameta.setOwner(member);
            }
        }
        for (String member : data.getMembers()) {
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(HexColor.hex(MenusFile.get(guiType).getString("gui.items.MEMBER.display-name").replace("{player}", member)));
            ArrayList<String> lore = new ArrayList<>();
            for (String str : MenusFile.get(guiType).getStringList("gui.items.MEMBER.lore")) {
                lore.add(HexColor.hex(str).replace("{status}", ClaimUtils.checkStatus(member)));
            }
            meta.setLore(lore);
            stack.setItemMeta(meta);
            NBTItem nbti = new NBTItem(stack);
            nbti.setString("owner", member);
            stack = nbti.getItem();
            inv.addItem(stack);
        }
    }
}

