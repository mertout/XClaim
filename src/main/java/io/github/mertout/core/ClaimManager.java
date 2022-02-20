package io.github.mertout.core;

import io.github.mertout.Claim;
import io.github.mertout.api.ClaimDeleteEvent;
import io.github.mertout.api.ClaimExpireEvent;
import io.github.mertout.core.data.DataHandler;
import io.github.mertout.core.timer.MoveTimer;
import io.github.mertout.filemanager.ClaimsFile;
import io.github.mertout.filemanager.MessagesFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ClaimManager {

    public void loadClaims() {
        ConfigurationSection sect = ClaimsFile.getClaimsFile().getConfigurationSection("claims");
        if (sect != null){
            for (String str : sect.getKeys(false)) {
                DataHandler data = new DataHandler();
                data.setChunk(ClaimsFile.getClaimsFile().getString("claims." + str + ".chunk"));
                data.setBlockLocation(ClaimsFile.getClaimsFile().getLocation("claims." + str + ".block-location"));
                data.setOwner(ClaimsFile.getClaimsFile().getString("claims." + str + ".owner"));
                data.setDay(ClaimsFile.getClaimsFile().getInt("claims." + str + ".day"));
                data.setHour(ClaimsFile.getClaimsFile().getInt("claims." + str + ".hour"));
                data.setMinutes(ClaimsFile.getClaimsFile().getInt("claims." + str + ".minutes"));
                data.setSeconds(ClaimsFile.getClaimsFile().getInt("claims." + str + ".seconds"));
                if (ClaimsFile.getClaimsFile().isList("claims." + data.getOwner() + ".members")) {
                    ClaimsFile.getClaimsFile().getStringList("claims." + data.getOwner() + ".members").forEach(lx -> data.getMembers().add(lx));
                }
                Claim.getInstance().getHologramCore().createHologram(data.getBlockLocation(), data.getOwner(), data);
                Claim.getInstance().getClaims().add(data);
            }
            ClaimsFile.getClaimsFile().set("claims", null);
            ClaimsFile.saveClaimsFile();
        }
    }
    public boolean hasClaimAtLoc(@NotNull Location loc, @NotNull Player p) {
        if (getChunk(loc) != null) {
            DataHandler data = getChunk(loc);
            return !data.getOwner().equals(p.getName()) && !data.getMembers().contains(p.getName());
        }
        return false;
    }
    public boolean hasClaim(@NotNull Player p) {
        for (DataHandler data : Claim.getInstance().getClaims()) {
            if (data.getOwner().equals(p.getName())) {
                return true;
            }
        }
        return false;
    }
    public DataHandler PlayerToClaim(@NotNull Player p) {
        if (hasClaim(p)) {
            for (DataHandler data : Claim.getInstance().getClaims()) {
                if (data.getOwner().equals(p.getName()) || data.getMembers().contains(p.getName())) {
                    return data;
                }
            }
        }
        return null;
    }
    public DataHandler getChunk(@NotNull Location loc) {
        for (DataHandler data : Claim.getInstance().getClaims()) {
            if (data.getChunk().equals(loc.getChunk().toString())) {
                return data;
            }
        }
        return null;
    }
    public void registerClaim(@NotNull Location loc, @NotNull Player p) {
        DataHandler data = new DataHandler();
        data.setChunk(loc.getChunk().toString());
        data.setBlockLocation(loc);
        data.setOwner(p.getName());
        data.setDay(29);
        data.setHour(23);
        data.setMinutes(59);
        data.setSeconds(59);
        Claim.getInstance().getClaims().add(data);
        Claim.getInstance().getHologramCore().createHologram(loc, p.getName(), data);
    }
    public void saveClaims() {
        ClaimsFile.getClaimsFile().set("claims", null);
        for (DataHandler data : Claim.getInstance().getClaims()) {
            ClaimsFile.getClaimsFile().set("claims." + data.getOwner() + ".chunk", data.getChunk());
            ClaimsFile.getClaimsFile().set("claims." + data.getOwner() + ".block-location", data.getBlockLocation());
            ClaimsFile.getClaimsFile().set("claims." + data.getOwner() + ".owner", data.getOwner());
            ClaimsFile.getClaimsFile().set("claims." + data.getOwner() + ".day", data.getDay());
            ClaimsFile.getClaimsFile().set("claims." + data.getOwner() + ".hour", data.getHour());
            ClaimsFile.getClaimsFile().set("claims." + data.getOwner() + ".minutes", data.getMinutes());
            ClaimsFile.getClaimsFile().set("claims." + data.getOwner() + ".seconds", data.getSeconds());
            if (data.getMembers() != null) {
                List<String> list = ClaimsFile.getClaimsFile().getStringList("claims." + data.getOwner() + ".members");
                data.getMembers().forEach(lx -> list.add(lx));
                ClaimsFile.getClaimsFile().set("claims." + data.getOwner() + ".members", list);
            }
            ClaimsFile.saveClaimsFile();
        }
    }
    public void deleteClaims(@NotNull Player p) {
        if (PlayerToClaim(p) != null) {
            DataHandler data = PlayerToClaim(p);
            if (data.getOwner().equals(p.getName())) {
                ClaimDeleteEvent event = new ClaimDeleteEvent(data);
                Bukkit.getServer().getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    Location loc = data.getBlockLocation();
                    loc.getBlock().setType(Material.AIR);
                    Claim.getInstance().getHologramCore().deleteHologram(data);
                    Claim.getInstance().getClaims().remove(data);
                    p.sendMessage(MessagesFile.convertString("messages.deleted-claim"));
                    Bukkit.broadcastMessage(MessagesFile.convertString("messages.claim-expired")
                            .replace("{x}", data.getBlockLocation().getX() + "")
                            .replace("{y}", data.getBlockLocation().getY() + "")
                            .replace("{z}", data.getBlockLocation().getZ() + "")
                            .replace("{world}", data.getBlockLocation().getWorld().getName()));
                }
            }
        }
    }
    public void deleteClaims(@NotNull DataHandler data) {
        ClaimExpireEvent event = new ClaimExpireEvent(data);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            Location loc = data.getBlockLocation();
            loc.getBlock().setType(Material.AIR);
            Claim.getInstance().getHologramCore().deleteHologram(data);
            Claim.getInstance().getClaims().remove(data);
            Bukkit.broadcastMessage(MessagesFile.convertString("messages.claim-expired")
                    .replace("{x}", data.getBlockLocation().getX() + "")
                    .replace("{y}", data.getBlockLocation().getY() + "")
                    .replace("{z}", data.getBlockLocation().getZ() + "")
                    .replace("{world}", data.getBlockLocation().getWorld().getName()));

        }
    }
    public static String calcTime(@NotNull DataHandler data) {
        return data.getDay() + Claim.getInstance().getConfig().getString("settings.hologram.time-format.days") + " " + data.getHour() + Claim.getInstance().getConfig().getString("settings.hologram.time-format.hours") + " " + data.getMinutes() + Claim.getInstance().getConfig().getString("settings.hologram.time-format.minutes") + " " + data.getSeconds() + Claim.getInstance().getConfig().getString("settings.hologram.time-format.seconds");
    }

    public void moveClaimBlock(Player p) {
        if (hasClaim(p)) {
            DataHandler data = PlayerToClaim(p);
            if (p.getLocation().getChunk().toString().equals(data.getChunk())) {
                if (!MoveTimer.getMoveTask().containsKey(p)) {
                    Location loc = data.getBlockLocation();
                    loc.getBlock().setType(Material.AIR);
                    data.setBlockLocation(p.getLocation().getBlock().getLocation());
                    p.getLocation().getBlock().setType(Material.BEDROCK);
                    Claim.getInstance().getMoveTimer().addMoveCooldown(p);
                    p.sendMessage(MessagesFile.convertString("messages.moved-claim-block"));
                }
                else {
                    int hour = 0;
                    int minute = 0;
                    int second = (int) MoveTimer.getMoveTask().get(p);
                    while (second > 3600) {
                        second = second - 3600;
                        hour++;
                    }
                    while (second > 60) {
                        second = second - 60;
                        minute++;
                    }
                    p.sendMessage(MessagesFile.convertString("messages.move-claim-block-cooldown")
                            .replace("{hour}", hour + "")
                            .replace("{minute}", minute + "")
                            .replace("{second}", second + ""));
                }
            }
            else {
                p.sendMessage(MessagesFile.convertString("messages.move-claim-block-error"));
            }
        }
    }
}
