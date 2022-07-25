package io.github.mertout.core;

import io.github.mertout.api.events.ClaimExpireEvent;
import io.github.mertout.filemanager.MessagesFile;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.Bukkit;
import io.github.mertout.api.events.ClaimDeleteEvent;
import java.util.List;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import io.github.mertout.Claim;
import io.github.mertout.core.data.DataHandler;
import io.github.mertout.filemanager.ClaimsFile;

public class ClaimManager
{
    public void loadClaims() {
        final ConfigurationSection sect = ClaimsFile.getClaimsFile().getConfigurationSection("claims");
        if (sect != null) {
            for (final String str : sect.getKeys(false)) {
                final DataHandler data = new DataHandler();
                data.setChunk(ClaimsFile.getClaimsFile().getString("claims." + str + ".chunk"));
                data.setBlockLocation((Location) ClaimsFile.getClaimsFile().get("claims." + str + ".block-location"));
                data.setOwner(ClaimsFile.getClaimsFile().getString("claims." + str + ".owner"));
                data.setDay(ClaimsFile.getClaimsFile().getInt("claims." + str + ".day"));
                data.setHour(ClaimsFile.getClaimsFile().getInt("claims." + str + ".hour"));
                data.setMinutes(ClaimsFile.getClaimsFile().getInt("claims." + str + ".minutes"));
                data.setSeconds(ClaimsFile.getClaimsFile().getInt("claims." + str + ".seconds"));
                if (ClaimsFile.getClaimsFile().isList("claims." + data.getOwner() + ".members")) {
                    ClaimsFile.getClaimsFile().getStringList("claims." + data.getOwner() + ".members").forEach(lx -> data.getMembers().add(lx));
                }
                Claim.getInstance().getHologramCore().createHologram(data.getBlockLocation(), data);
                Claim.getInstance().getClaims().add(data);
            }
            ClaimsFile.getClaimsFile().set("claims", null);
            ClaimsFile.saveClaimsFile();
        }
    }

    public boolean hasClaimAtLoc(@NotNull final Location loc, @NotNull final Player p) {
        if (this.getChunk(loc) != null) {
            final DataHandler data = this.getChunk(loc);
            return !data.getOwner().equals(p.getName()) && !data.getMembers().contains(p.getName());
        }
        return false;
    }

    public boolean hasClaim(@NotNull final Player p) {
        for (final DataHandler data : Claim.getInstance().getClaims()) {
            if (data.getOwner().equals(p.getName())) {
                return true;
            }
        }
        return false;
    }
    public DataHandler PlayerToClaim(@NotNull Player p) {
        if (hasClaim(p)) {
            for (DataHandler data : Claim.getInstance().getClaims()) {
                if (data.getOwner().equals(p.getName())) {
                    return data;
                }
            }
        }
        return null;
    }
    public DataHandler PlayerToClaim2(@NotNull Player p) {
        if (hasClaim(p)) {
            for (DataHandler data : Claim.getInstance().getClaims()) {
                if (data.getOwner().equals(p.getName())) {
                    return data;
                }
            }
            for (DataHandler data : Claim.getInstance().getClaims()) {
                if (data.getMembers().contains(p.getName())) {
                    return data;
                }
            }
        }
        return null;
    }
    public DataHandler getChunk(@NotNull final Location loc) {
        for (final DataHandler data : Claim.getInstance().getClaims()) {
            if (data.getChunk().equals(loc.getChunk().toString())) {
                if (data.getBlockLocation().getWorld().toString().equals(loc.getChunk().getWorld().toString())) {
                    return data;
                }
            }
        }
        return null;
    }

    public DataHandler getChunk(@NotNull final Chunk chunk) {
        for (final DataHandler data : Claim.getInstance().getClaims()) {
            if (data.getChunk().equals(chunk.toString())) {
                if (data.getBlockLocation().getWorld().toString().equals(chunk.getWorld().toString())) {
                    return data;
                }
            }
        }
        return null;
    }

    public void registerClaim(@NotNull final Location loc, @NotNull final Player p) {
        final DataHandler data = new DataHandler();
        data.setChunk(loc.getChunk().toString());
        data.setBlockLocation(loc);
        data.setOwner(p.getName());
        data.setDay(29);
        data.setHour(23);
        data.setMinutes(59);
        data.setSeconds(59);
        Claim.getInstance().getClaims().add(data);
        Claim.getInstance().getHologramCore().createHologram(loc, data);
    }

    public void saveClaims() {
        ClaimsFile.getClaimsFile().set("claims", null);
        for (final DataHandler data : Claim.getInstance().getClaims()) {
            ClaimsFile.getClaimsFile().set("claims." + data.getOwner() + ".chunk", (Object)data.getChunk());
            ClaimsFile.getClaimsFile().set("claims." + data.getOwner() + ".block-location", (Object)data.getBlockLocation());
            ClaimsFile.getClaimsFile().set("claims." + data.getOwner() + ".owner", (Object)data.getOwner());
            ClaimsFile.getClaimsFile().set("claims." + data.getOwner() + ".day", (Object)data.getDay());
            ClaimsFile.getClaimsFile().set("claims." + data.getOwner() + ".hour", (Object)data.getHour());
            ClaimsFile.getClaimsFile().set("claims." + data.getOwner() + ".minutes", (Object)data.getMinutes());
            ClaimsFile.getClaimsFile().set("claims." + data.getOwner() + ".seconds", (Object)data.getSeconds());
            if (data.getMembers() != null) {
                final List<String> list = ClaimsFile.getClaimsFile().getStringList("claims." + data.getOwner() + ".members");
                data.getMembers().forEach(lx -> list.add(lx));
                ClaimsFile.getClaimsFile().set("claims." + data.getOwner() + ".members", (Object)list);
            }
            ClaimsFile.saveClaimsFile();
        }
    }

    public void deleteClaims(@NotNull final Player p) {
        if (this.hasClaim(p)) {
            final DataHandler data = this.PlayerToClaim(p);
            if (data.getOwner().equals(p.getName())) {
                final ClaimDeleteEvent event = new ClaimDeleteEvent(data, p);
                Bukkit.getServer().getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    final Location loc = data.getBlockLocation();
                    loc.getBlock().setType(Material.AIR);
                    Claim.getInstance().getHologramCore().deleteHologram(data);
                    Claim.getInstance().getClaims().remove(data);
                    p.sendMessage(MessagesFile.convertString("messages.deleted-claim"));
                    Bukkit.broadcastMessage(MessagesFile.convertString("messages.claim-expired").replace("{x}", data.getBlockLocation().getX() + "").replace("{y}", data.getBlockLocation().getY() + "").replace("{z}", data.getBlockLocation().getZ() + "").replace("{world}", data.getBlockLocation().getWorld().getName()));
                }
            }
        }
    }

    public void deleteClaims(@NotNull final DataHandler data) {
        final ClaimExpireEvent event = new ClaimExpireEvent(data);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            final Location loc = data.getBlockLocation();
            loc.getBlock().setType(Material.AIR);
            if (Claim.getInstance().getConfig().getString("settings.claim-expired-broadcast").equals("ALL")) {
                Bukkit.broadcastMessage(MessagesFile.convertString("messages.claim-expired").replace("{x}", data.getBlockLocation().getX() + "").replace("{y}", data.getBlockLocation().getY() + "").replace("{z}", data.getBlockLocation().getZ() + "").replace("{world}", data.getBlockLocation().getWorld().getName()));
            }
            else {
                data.getBlockLocation().getWorld().getPlayers().forEach(p -> p.sendMessage(MessagesFile.convertString("messages.claim-expired").replace("{x}", data.getBlockLocation().getX() + "").replace("{y}", data.getBlockLocation().getY() + "").replace("{z}", data.getBlockLocation().getZ() + "").replace("{world}", data.getBlockLocation().getWorld().getName())));
            }
            Claim.getInstance().getHologramCore().deleteHologram(data);
            Claim.getInstance().getClaims().remove(data);
        }
    }

    public static String calcTime(@NotNull final DataHandler data) {
        return data.getDay() + Claim.getInstance().getConfig().getString("claim-hologram.settings.time-format.days") + " " + data.getHour() + Claim.getInstance().getConfig().getString("claim-hologram.settings.time-format.hours") + " " + data.getMinutes() + Claim.getInstance().getConfig().getString("claim-hologram.settings.time-format.minutes") + " " + data.getSeconds() + Claim.getInstance().getConfig().getString("claim-hologram.settings.time-format.seconds");
    }
}
