package io.github.mertout.core;

import io.github.mertout.api.events.ClaimExpireEvent;
import io.github.mertout.filemanager.files.MessagesFile;
import io.github.mertout.holograms.file.HologramFile;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.Bukkit;
import io.github.mertout.api.events.ClaimDeleteEvent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import io.github.mertout.Claim;
import io.github.mertout.core.data.DataHandler;
import io.github.mertout.filemanager.files.ClaimsFile;

public class ClaimManager
{
    public void loadClaims() throws InterruptedException {
        final ConfigurationSection sect = ClaimsFile.getClaimsFile().getConfigurationSection("claims");
        if (sect != null) {
            int total = sect.getKeys(false).size();
            AtomicInteger loaded = new AtomicInteger(0);
            new BukkitRunnable() {
                public void run() {
                    if (loaded.get() != total) {
                        for (final String str : sect.getKeys(false)) {
                            loaded.incrementAndGet();
                            double calc = (loaded.get() / (double) total) * 100;
                            int val = (int) Math.round(calc);
                            System.out.println("Loading Claims! " + val + "%");
                            final DataHandler data = new DataHandler();
                            data.setChunk(ClaimsFile.getClaimsFile().getString("claims." + str + ".chunk"));
                            data.setBlockLocation((Location) ClaimsFile.getClaimsFile().get("claims." + str + ".block-location"));
                            data.setOwner(ClaimsFile.getClaimsFile().getString("claims." + str + ".owner"));
                            data.setDay(ClaimsFile.getClaimsFile().getInt("claims." + str + ".day"));
                            data.setHour(ClaimsFile.getClaimsFile().getInt("claims." + str + ".hour"));
                            data.setMinutes(ClaimsFile.getClaimsFile().getInt("claims." + str + ".minutes"));
                            data.setSeconds(ClaimsFile.getClaimsFile().getInt("claims." + str + ".seconds"));
                            if (ClaimsFile.getClaimsFile().isSet("claims." + str + ".creation-date")) {
                                data.setCreationDate(ClaimsFile.getClaimsFile().getString("claims." + str + ".creation-date"));
                            }
                                data.setCreationDate(LocalDate.now().toString());
                            if (ClaimsFile.getClaimsFile().isList("claims." + data.getOwner() + ".members")) {
                                ClaimsFile.getClaimsFile().getStringList("claims." + data.getOwner() + ".members").forEach(lx -> data.getMembers().add(lx));
                            }
                            Claim.getInstance().getHologramCore().createHologram(data.getBlockLocation(), data);
                            Claim.getInstance().getClaims().add(data);
                        }
                    }
                    else {
                        cancel();
                        System.out.println("Loaded Claims!");
                    }
                }
            }.runTaskTimerAsynchronously(Claim.getInstance(), 1, total);
        }
    }

    public boolean hasClaimAtLocation(@NotNull final Location loc, @NotNull final Player p) {
        if (this.getChunkClaim(loc) != null) {
            final DataHandler data = this.getChunkClaim(loc);
            if (!Claim.getInstance().getAdminBypassList().contains(p)) { return !data.getOwner().equals(p.getName()) && !data.getMembers().contains(p.getName()); }
        }
        return false;
    }

    public boolean hasClaim(@NotNull final Player p) {
        for (final DataHandler data : Claim.getInstance().getClaims()) {
            if (data.getOwner().equals(p.getName())) { return true; }
        }
        return false;
    }
    public DataHandler getPlayerClaim(@NotNull Player p) {
        if (hasClaim(p)) {
            for (DataHandler data : Claim.getInstance().getClaims()) {
                if (data.getOwner().equals(p.getName()) || data.getMembers().contains(p.getName())) {
                    return data;
                }
            }
        }
        return null;
    }
    public DataHandler getChunkClaim(@NotNull final Location loc) {
        for (final DataHandler data : Claim.getInstance().getClaims()) {
            if (data.getChunk().equals(loc.getChunk().toString())) {
                if (data.getBlockLocation().getWorld().toString().equals(loc.getChunk().getWorld().toString())) {
                    return data;
                }
            }
        }
        return null;
    }
    public DataHandler getChunkClaim(@NotNull final Chunk chunk) {
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
        data.setDay((Claim.getInstance().getConfig().getInt("settings.claim-day") - 1));
        data.setHour(23);
        data.setMinutes(59);
        data.setSeconds(59);
        data.setCreationDate(LocalDate.now().toString());
        Claim.getInstance().getClaims().add(data);
        Claim.getInstance().getHologramCore().createHologram(loc, data);
    }

    public void saveClaims() {
        ClaimsFile.getClaimsFile().set("claims", null);
        ClaimsFile.saveClaimsFile();
        long start = System.currentTimeMillis();
        for (DataHandler data : Claim.getInstance().getClaims()) {
            ClaimsFile.getClaimsFile().set("claims." + data.getOwner() + ".chunk", data.getChunk());
            ClaimsFile.getClaimsFile().set("claims." + data.getOwner() + ".block-location", data.getBlockLocation());
            ClaimsFile.getClaimsFile().set("claims." + data.getOwner() + ".owner", data.getOwner());
            ClaimsFile.getClaimsFile().set("claims." + data.getOwner() + ".day", data.getDay());
            ClaimsFile.getClaimsFile().set("claims." + data.getOwner() + ".hour", data.getHour());
            ClaimsFile.getClaimsFile().set("claims." + data.getOwner() + ".minutes", data.getMinutes());
            ClaimsFile.getClaimsFile().set("claims." + data.getOwner() + ".seconds", data.getSeconds());
            ClaimsFile.getClaimsFile().set("claims." + data.getOwner() + ".creation-date", data.getCreationDate());
            if (data.getMembers() != null) {
                List<String> list = ClaimsFile.getClaimsFile().getStringList("claims." + data.getOwner() + ".members");
                data.getMembers().forEach(lx -> list.add(lx));
                ClaimsFile.getClaimsFile().set("claims." + data.getOwner() + ".members", list);
            }
            ClaimsFile.saveClaimsFile();
        }
        System.out.println("Saved All Data! " + (System.currentTimeMillis() - start) + "ms");
    }

    public void deleteClaim(@NotNull final Player p) {
        if (!this.hasClaim(p)) {
            return;
        }
        final DataHandler data = this.getPlayerClaim(p);
        if (!data.getOwner().equals(p.getName())) {
            return;
        }
        final ClaimDeleteEvent event = new ClaimDeleteEvent(data, p);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        final Location loc = data.getBlockLocation();
        loc.getBlock().setType(Material.AIR);
        Claim.getInstance().getHologramCore().deleteHologram(data);
        p.sendMessage(MessagesFile.convertString("messages.deleted-claim"));
        final String broadcastMsg = MessagesFile.convertString("messages.claim-expired")
                .replace("{x}", data.getBlockLocation().getX() + "")
                .replace("{y}", data.getBlockLocation().getY() + "")
                .replace("{z}", data.getBlockLocation().getZ() + "")
                .replace("{world}", data.getBlockLocation().getWorld().getName());
        Bukkit.broadcastMessage(broadcastMsg);
        Claim.getInstance().getClaims().remove(data);
    }

    public void deleteClaim(@NotNull final DataHandler data) {
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
        final String dayFormat = HologramFile.get().getString("settings.time-format.days");
        final String hourFormat = HologramFile.get().getString("settings.time-format.hours");
        final String minuteFormat = HologramFile.get().getString("settings.time-format.minutes");
        final String secondFormat = HologramFile.get().getString("settings.time-format.seconds");

        return data.getDay() + dayFormat + " " + data.getHour() + hourFormat + " " + data.getMinutes() + minuteFormat + " " + data.getSeconds() + secondFormat;
    }
}
