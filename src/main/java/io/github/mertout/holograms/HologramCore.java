package io.github.mertout.holograms;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import eu.decentsoftware.holograms.api.DHAPI;
import io.github.mertout.core.ClaimManager;
import io.github.mertout.utils.HexColor;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import io.github.mertout.core.data.DataHandler;
import org.jetbrains.annotations.NotNull;
import org.bukkit.Location;
import io.github.mertout.Claim;
import org.bukkit.Bukkit;

public class HologramCore {

    public void installHologramPlugin() {
        if (Bukkit.getPluginManager().getPlugin("DecentHolograms") == null && Bukkit.getPluginManager().getPlugin("HolographicDisplays") == null) {
            Claim.getInstance().getServer().getPluginManager().disablePlugin(Claim.getInstance());
            System.out.println("Hologram plugin not found. Please install Decent Holograms or Holographic Displays!");
        }
    }

    public void createHologram(@NotNull final Location loc, @NotNull final DataHandler data) {
        Location loc2 = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
        loc2.setX(loc2.getX() + 0.5);
        loc2.setY(loc2.getY() + Claim.getInstance().getConfig().getInt("claim-hologram.settings.hologram-height"));
        loc2.setZ(loc2.getZ() + 0.5);
        if (Bukkit.getPluginManager().getPlugin("DecentHolograms") != null) {
            final eu.decentsoftware.holograms.api.holograms.Hologram holo = DHAPI.createHologram(data.getOwner(), loc2);
            for (String str : Claim.getInstance().getConfig().getStringList("claim-hologram.settings.hologram-lines")) {
                DHAPI.addHologramLine(holo, HexColor.hex(str.replace("{owner}", data.getOwner()).replace("{remaining-time}", ClaimManager.calcTime(data)).replace("{team-size}", data.getMembers().size() + "")));
            }
            data.setHoloDH(holo);
        }
        else if (Bukkit.getPluginManager().getPlugin("HolographicDisplays") != null) {
            final Hologram holo = HologramsAPI.createHologram(Claim.getInstance(), loc2);
            for (String str : Claim.getInstance().getConfig().getStringList("claim-hologram.settings.hologram-lines")) {
                holo.appendTextLine(HexColor.hex(str.replace("{owner}", data.getOwner()).replace("{remaining-time}", ClaimManager.calcTime(data)).replace("{team-size}", data.getMembers().size() + "")));
            }
            data.setHoloHD(holo);
        }
    }
    public void deleteHologram(@NotNull final DataHandler data) {
        if (Bukkit.getPluginManager().getPlugin("DecentHolograms") != null) {
            data.getHoloDH().destroy();
            data.getHoloDH().delete();
        }
        else if (Bukkit.getPluginManager().getPlugin("HolographicDisplays") != null) {
            data.getHoloHD().delete();
        }
    }
    public void moveHologram(@NotNull final DataHandler data, Location loc) {
        Location loc2 = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
        if (Bukkit.getPluginManager().getPlugin("DecentHolograms") != null) {
            loc2.setX(loc2.getX() + 0.5);
            loc2.setY(loc2.getY() + Claim.getInstance().getConfig().getInt("claim-hologram.settings.hologram-height"));
            loc2.setZ(loc2.getZ() + 0.5);
            DHAPI.moveHologram(data.getHoloDH(), loc2);
        }
        else if (Bukkit.getPluginManager().getPlugin("HolographicDisplays") != null) {
            data.getHoloHD().delete();
            this.createHologram(loc2, data);
        }
    }
    public void updateHolograms() {
        for (final DataHandler data : Claim.getInstance().getClaims()) {
            data.setSeconds(data.getSeconds() - 1);
            if (data.getSeconds() == 0) {
                data.setMinutes(data.getMinutes() - 1);
                data.setSeconds(59);
            }
            if (data.getMinutes() == 0) {
                data.setHour(data.getHour() - 1);
                data.setMinutes(59);
                data.setSeconds(59);
            }
            if (data.getHour() == 0) {
                data.setDay(data.getDay() - 1);
                data.setHour(23);
                data.setMinutes(59);
                data.setSeconds(59);
            }
            if (data.getDay() == 0 && data.getHour() == 0 && data.getMinutes() == 0 && data.getSeconds() == 0) {
                Claim.getInstance().getClaimManager().deleteClaims(data);
            }
            if (Bukkit.getServer().getOnlinePlayers().size() > 0) {
                if (Bukkit.getPluginManager().getPlugin("DecentHolograms") != null) {
                    final eu.decentsoftware.holograms.api.holograms.Hologram holo = data.getHoloDH();
                    int index = 0;
                    for (String str : Claim.getInstance().getConfig().getStringList("claim-hologram.settings.hologram-lines")) {
                        DHAPI.setHologramLine(holo, index, HexColor.hex(str.replace("{owner}", data.getOwner()).replace("{remaining-time}", ClaimManager.calcTime(data)).replace("{team-size}", data.getMembers().size() + "")));
                        index++;
                    }
                    data.setHoloDH(holo);
                }
                else if (Bukkit.getPluginManager().getPlugin("HolographicDisplays") != null) {
                    final Hologram holo = data.getHoloHD();
                    holo.clearLines();
                    for (String str : Claim.getInstance().getConfig().getStringList("claim-hologram.settings.hologram-lines")) {
                        holo.appendTextLine(HexColor.hex(str.replace("{owner}", data.getOwner()).replace("{remaining-time}", ClaimManager.calcTime(data)).replace("{team-size}", data.getMembers().size() + "")));
                    }

                    data.setHoloHD(holo);

                }
            }
        }
    }
}