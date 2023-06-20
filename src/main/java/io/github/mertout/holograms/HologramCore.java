package io.github.mertout.holograms;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import io.github.mertout.Claim;
import io.github.mertout.core.ClaimManager;
import io.github.mertout.holograms.file.HologramFile;
import io.github.mertout.utils.HexColor;
import io.github.mertout.core.data.DataHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class HologramCore {

    public void setupHolograms() {
        if (Bukkit.getPluginManager().getPlugin("DecentHolograms") == null) {
            Claim.getInstance().getServer().getPluginManager().disablePlugin(Claim.getInstance());
            System.out.println("Hologram plugin not found. Please install Decent Holograms");
        }
    }

    public void createHologram(final Location loc, final DataHandler data) {
        Location loc2 = loc.clone().add(0.5, HologramFile.get().getDouble("settings.hologram-height"), 0.5);
        String worldName = loc.getWorld().getName();
        int chunkX = loc.getChunk().getX();
        int chunkZ = loc.getChunk().getZ();

        String var;
        if (chunkX >= 0 && chunkZ >= 0) {
            var = worldName + "_X" + chunkX + "_Z" + chunkZ;
        } else if (chunkX < 0 && chunkZ >= 0) {
            var = worldName + "_Xm" + Math.abs(chunkX) + "_Z" + chunkZ;
        } else if (chunkX >= 0 && chunkZ < 0) {
            var = worldName + "_X" + chunkX + "_Zm" + Math.abs(chunkZ);
        } else {
            var = worldName + "_Xm" + Math.abs(chunkX) + "_Zm" + Math.abs(chunkZ);
        }

        if (Bukkit.getPluginManager().getPlugin("DecentHolograms") != null) {
            Hologram hologram = DHAPI.getHologram(var);
            if (hologram != null) {
                hologram.destroy();
            }
            hologram = DHAPI.createHologram(var, loc2);
            for (String str : HologramFile.get().getStringList("settings.hologram-lines")) {
                DHAPI.addHologramLine(hologram, HexColor.hex(str
                        .replace("{owner}", data.getOwner())
                        .replace("{remaining-time}", ClaimManager.calcTime(data))
                        .replace("{team-size}", String.valueOf(data.getMembers().size()))
                        .replace("{creation-date}", data.getCreationDate())));
            }
            data.setHologram(hologram);
        }
    }

    public void deleteHologram(final DataHandler data) {
        if (Bukkit.getPluginManager().getPlugin("DecentHolograms") != null) {
            Hologram hologram = data.getHologram();
            if (hologram != null) {
                hologram.destroy();
                hologram.delete();
            }
        }
    }

    public void moveHologram(final DataHandler data, Location loc) {
        if (Bukkit.getPluginManager().getPlugin("DecentHolograms") != null) {
            Location loc2 = loc.clone().add(0.5, HologramFile.get().getInt("settings.hologram-height"), 0.5);
            DHAPI.moveHologram(data.getHologram(), loc2);
        }
    }

    public void updateHolograms() {
        for (final DataHandler data : Claim.getInstance().getClaims()) {
            data.setSeconds(data.getSeconds() - 1);
            if (data.getSeconds() <= 0) {
                data.setMinutes(data.getMinutes() - 1);
                data.setSeconds(59);
            }
            if (data.getMinutes() <= 0) {
                data.setHour(data.getHours() - 1);
                data.setMinutes(59);
                data.setSeconds(59);
            }
            if (data.getHours() <= 0) {
                data.setDay(data.getDays() - 1);
                data.setHour(23);
                data.setMinutes(59);
                data.setSeconds(59);
            }
            if (data.getDays() <=0 && data.getHours() <=0 && data.getMinutes() <=0 && data.getSeconds() <= 0) {
                Claim.getInstance().getClaimManager().deleteClaim(data);
                return;
            }
            if (!Bukkit.getOnlinePlayers().isEmpty()) {
                if (Bukkit.getPluginManager().getPlugin("DecentHolograms") != null && data.getHologram() != null) {
                    Hologram hologram = data.getHologram();
                    int index = 0;
                    for (String str : HologramFile.get().getStringList("settings.hologram-lines")) {
                        DHAPI.setHologramLine(hologram, index, HexColor.hex(str
                                .replace("{owner}", data.getOwner())
                                .replace("{remaining-time}", ClaimManager.calcTime(data))
                                .replace("{team-size}", String.valueOf(data.getMembers().size()))
                                .replace("{creation-date}", data.getCreationDate())));
                        index++;
                    }
                    data.setHologram(hologram);
                }
            }
        }
    }
}
