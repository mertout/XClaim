package io.github.mertout.core;

import de.tr7zw.changeme.nbtapi.NBTItem;
import io.github.mertout.Claim;
import io.github.mertout.api.events.ClaimMemberAddEvent;
import io.github.mertout.core.data.DataHandler;
import io.github.mertout.filemanager.files.MenusFile;
import io.github.mertout.filemanager.files.MessagesFile;
import io.github.mertout.gui.GuiType;
import io.github.mertout.utils.ClaimUtils;
import io.github.mertout.utils.HexColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemberManager {

    public int getMemberSize(DataHandler claim) {
        int memberSize = 0;
        if (claim.getMembers().size() > 0) {
            memberSize = claim.getMembers().size();
        }
        return memberSize;
    }
    public int getMaxMemberSize(Player p) {
        int maxMembers = Claim.getInstance().getConfig().getInt("settings.default-max-member-in-claim");
        for (int i = 100; i >= 1; i--) {
            if (p.hasPermission("xclaim.max.member." + i)) {
                maxMembers = i;
                break;
            }
        }
        return maxMembers;
    }
    public boolean canAddAnyMember(Player p, DataHandler claim) {
        return (getMaxMemberSize(p) > getMemberSize(claim));
    }
    public void addMemberToClaim(Player p, DataHandler claim, Player p2) {
        Bukkit.getScheduler().runTask(Claim.getInstance(), () -> {
            ClaimMemberAddEvent event = new ClaimMemberAddEvent(claim, p, p2);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return;
            }
            claim.getMembers().add(p2.getName());
        });
    }
    public void openMemberPage(Player p, int page, int startIndex) {
        GuiType guiType = GuiType.MEMBERS;
        DataHandler data = Claim.getInstance().getClaimManager().getChunkClaim(p.getLocation());
        if (data == null || data.getMembers() == null || data.getMembers().isEmpty()) {
            p.closeInventory();
            p.sendMessage(MessagesFile.convertString("messages.no-player-in-claim"));
            return;
        }
        ConfigurationSection sect = MenusFile.get(guiType).getConfigurationSection("gui.items");
        if (sect == null) {
            System.out.println("ERROR! CREATING GUI " + guiType);
            return;
        }
        Inventory inv = Bukkit.createInventory(null, MenusFile.get(guiType).getInt("gui.size") * 9, HexColor.hex(MenusFile.get(guiType).getString("gui.title")).replace("{page}", page + ""));

        List<String> members = new ArrayList<>(data.getMembers());
        Collections.sort(members);

        for (final String str : sect.getKeys(false)) {
            if (!str.equals("MEMBER")) {
                String mat = MenusFile.get(guiType).getString("gui.items." + str + ".material");
                List<Integer> slots = new ArrayList<>();
                if (MenusFile.get(guiType).isSet("gui.items." + str + ".slot")) {
                    slots.add(MenusFile.get(guiType).getInt("gui.items." + str + ".slot"));
                } else if (MenusFile.get(guiType).isSet("gui.items." + str + ".slots")) {
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
                ItemStack is = new ItemStack(Material.matchMaterial(mat));
                final ItemMeta im = is.getItemMeta();
                if (MenusFile.get(guiType).isSet("gui.items." + str + ".display-name")) {
                    im.setDisplayName(HexColor.hex(MenusFile.get(guiType).getString("gui.items." + str + ".display-name")).replace("{page}", String.valueOf(page)));
                }
                final ArrayList<String> lore = new ArrayList<>();
                for (final String str2 : MenusFile.get(guiType).getStringList("gui.items." + str + ".lore")) {
                    lore.add(HexColor.hex(str2));
                }
                im.setLore(lore);
                is.setItemMeta(im);
                if (str.equals("NEXT-PAGE")) {
                    NBTItem nbti = new NBTItem(is);
                    nbti.setString("page", "next");
                    is = nbti.getItem();
                } else if (str.equals("PREVIOUS-PAGE")) {
                    NBTItem nbti = new NBTItem(is);
                    nbti.setString("page", "previous");
                    is = nbti.getItem();
                } else if (str.equals("CURRENT-PAGE")) {
                    NBTItem nbti = new NBTItem(is);
                    nbti.setString("currentpage", String.valueOf(page));
                    is = nbti.getItem();
                }
                for (int num : slots) {
                    inv.setItem(num, is);
                }
            }
        }
        if (calculateInventoryCapacity(inv) > 0) {
            startIndex = startIndex * (page - 1);
            addMemberToGui(inv, members,  startIndex);
        }
        p.openInventory(inv);
    }

    public void addMemberToGui(Inventory inv, List<String> members, int startIndex) {
        GuiType guiType = GuiType.MEMBERS;
        for (int i = startIndex; i < members.size(); i++) {
            if (calculateInventoryCapacity(inv) > 0 && members.get(i) != null) {
                String member = members.get(i);
                ItemStack stack = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short) 3);
                SkullMeta skullMeta = (SkullMeta) stack.getItemMeta();

                skullMeta.setOwner(member);
                stack.setItemMeta(skullMeta);

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
            else {
                return;
            }
        }
    }

    public int calculateInventoryCapacity(Inventory inventory) {
        int emptySlots = 0;

        for (int slot = 0; slot < inventory.getSize(); slot++) {
            if (inventory.getItem(slot) == null) {
                emptySlots++;
            }
        }

        return emptySlots;
    }
    public int findPage(Inventory inv) {
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack item = inv.getItem(i);
            if (item != null && item.getType() != Material.AIR) {
                NBTItem nbtItem = new NBTItem(item);
                if (nbtItem.hasKey("currentpage")) {
                    return Integer.parseInt(nbtItem.getString("currentpage"));
                }
            }
        }
        return 1;
    }
    public int calculateSkull(Inventory inventory) {
        int skulls = 0;

        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.getType() == Material.PLAYER_HEAD) {
                skulls += item.getAmount();
            }
        }

        return skulls;
    }
}
