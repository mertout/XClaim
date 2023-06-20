package io.github.mertout.core;

import io.github.mertout.api.events.ClaimExpireEvent;
import io.github.mertout.filemanager.files.MessagesFile;
import io.github.mertout.holograms.file.HologramFile;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.Bukkit;
import io.github.mertout.api.events.ClaimDeleteEvent;

import java.time.LocalDate;
import java.util.ArrayList;
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
    public void loadClaims() {
        ConfigurationSection sect = ClaimsFile.getClaimsFile().getConfigurationSection("claims");
        if (sect != null) {
            int total = sect.getKeys(false).size();
            AtomicInteger loaded = new AtomicInteger(0);

            BukkitRunnable task = new BukkitRunnable() {
                public void run() {
                    int currentLoaded = loaded.incrementAndGet();
                    double calc = (currentLoaded / (double) total) * 100;
                    int val = (int) Math.round(calc);
                    Claim.getInstance().getLogger().info("Loading Claims! " + val + "%");

                    if (currentLoaded >= total) {
                        cancel();
                        Claim.getInstance().getLogger().info("Loaded Claims!");
                    }
                }
            };
            //for new claims
            if (hasBlockLocation()) {
                for (String world : sect.getKeys(false)) {
                    for (String str : ClaimsFile.getClaimsFile().getConfigurationSection("claims." + world).getKeys(false)) {
                        loadClaim(ClaimsFile.getClaimsFile().getConfigurationSection("claims." + world + "." + str), str);
                    }
                }
            }
            //for old claims
            else {
                for (String old : sect.getKeys(false)) {
                    loadClaim(ClaimsFile.getClaimsFile().getConfigurationSection("claims." + old), ClaimsFile.getClaimsFile().getConfigurationSection("claims." + old).getString("chunk"));
                }
            }
            task.runTaskTimerAsynchronously(Claim.getInstance(), 1, 1);
        }
    }

    public boolean hasBlockLocation() {
        ConfigurationSection claimsSection = ClaimsFile.getClaimsFile().getConfigurationSection("claims");
        if (claimsSection != null) {
            for (String key : claimsSection.getKeys(false)) {
                if (claimsSection.isConfigurationSection(key)) {
                    ConfigurationSection subsection = claimsSection.getConfigurationSection(key);
                    for (String key2 : subsection.getKeys(false)) {
                        if (ClaimsFile.getClaimsFile().isSet("claims" + "." + key + "." + key2 + ".block-location")) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void loadClaim(ConfigurationSection section, String str) {
        final DataHandler data = new DataHandler();
        data.setChunk(str);
        data.setBlockLocation((Location) section.get("block-location"));
        data.setOwner(section.getString("owner"));
        data.setDay(section.getInt("day"));
        data.setHour(section.getInt("hour"));
        data.setMinutes(section.getInt("minutes"));
        data.setSeconds(section.getInt("seconds"));
        if (section.isSet("creation-date")) {
            data.setCreationDate(section.getString("creation-date"));
        }
        else {
            data.setCreationDate(LocalDate.now().toString());
        }
        if (section.isList("members")) {
            for (String mem : section.getStringList("members")) {
                if (!data.getMembers().contains(mem)) {
                    data.getMembers().add(mem);
                }
            }
        }
        Claim.getInstance().getQueueHolograms().addQueueHolograms(data);
        Claim.getInstance().getClaims().add(data);
    }

    public boolean hasClaimAtLocation(@NotNull final Location loc, @NotNull final Player p) {
        if (this.getChunkClaim(loc) != null) {
            final DataHandler data = this.getChunkClaim(loc);
            if (!Claim.getInstance().getAdminBypassList().contains(p)) { return !data.getOwner().equals(p.getName()) && !data.getMembers().contains(p.getName()); }
        }
        return false;
    }

    public List<DataHandler> getPlayerClaims(@NotNull Player p) {
        List<DataHandler> playerClaims = new ArrayList<>();
        for (DataHandler data : Claim.getInstance().getClaims()) {
            if (data.getOwner().equals(p.getName()) || data.getMembers().contains(p.getName())) {
                playerClaims.add(data);
            }
        }
        return playerClaims;
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
            ClaimsFile.getClaimsFile().set("claims." + data.getBlockLocation().getWorld().getName() + "." + data.getChunk().toString() + ".block-location", data.getBlockLocation());
            ClaimsFile.getClaimsFile().set("claims." + data.getBlockLocation().getWorld().getName() + "." + data.getChunk().toString() + ".owner", data.getOwner());
            ClaimsFile.getClaimsFile().set("claims." + data.getBlockLocation().getWorld().getName() + "." + data.getChunk().toString() + ".day", data.getDays());
            ClaimsFile.getClaimsFile().set("claims." + data.getBlockLocation().getWorld().getName() + "." + data.getChunk().toString() + ".hour", data.getHours());
            ClaimsFile.getClaimsFile().set("claims." + data.getBlockLocation().getWorld().getName() + "." + data.getChunk().toString() + ".minutes", data.getMinutes());
            ClaimsFile.getClaimsFile().set("claims." + data.getBlockLocation().getWorld().getName() + "." + data.getChunk().toString() + ".seconds", data.getSeconds());
            ClaimsFile.getClaimsFile().set("claims." + data.getBlockLocation().getWorld().getName() + "." + data.getChunk().toString() + ".creation-date", data.getCreationDate());
            if (data.getMembers() != null) {
                List<String> list = ClaimsFile.getClaimsFile().getStringList("claims." + data.getBlockLocation().getWorld().getName() + "." + data.getChunk().toString() + ".members");
                data.getMembers().forEach(mem -> list.add(mem));
                ClaimsFile.getClaimsFile().set("claims." + data.getBlockLocation().getWorld().getName() + "." + data.getChunk().toString() + ".members", list);
            }
            ClaimsFile.saveClaimsFile();
        }
        Claim.getInstance().getLogger().info("Saved All Data! " + (System.currentTimeMillis() - start) + "ms");
    }

    public void deleteClaim(@NotNull final Player p, @NotNull final DataHandler data) {
        if (!data.getOwner().equals(p.getName()) && data.getChunk().equals(p.getLocation().getChunk().toString())) {
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

        return data.getDays() + dayFormat + " " + data.getHours() + hourFormat + " " + data.getMinutes() + minuteFormat + " " + data.getSeconds() + secondFormat;
    }
    public int getClaimsSize(Player p) {
        int claim = 0;
        for (DataHandler data : Claim.getInstance().getClaims()) {
            if (data.getOwner().equals(p.getName())) {
                claim++;
            }
        }
        return claim;
    }
    public int getMaxClaimsSize(Player p) {
        int maxClaim = Claim.getInstance().getConfig().getInt("settings.default-max-per-claim");
        for (int i = 100; i >= 1; i--) {
            if (p.hasPermission("xclaim.max.claim." + i)) {
                maxClaim = i;
                break;
            }
        }
        return maxClaim;
    }
    public boolean canCreateClaim(Player p) {
        return (getMaxClaimsSize(p) > getClaimsSize(p));
    }
}
