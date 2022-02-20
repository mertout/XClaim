package io.github.mertout.holograms;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import io.github.mertout.Claim;
import io.github.mertout.core.ClaimManager;
import io.github.mertout.core.data.DataHandler;
import io.github.mertout.addons.HexColor;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getServer;

public class HologramCore {

    public void createHologram(@NotNull Location loc, @NotNull String owner,@NotNull DataHandler data) {
        Hologram holo = HologramsAPI.createHologram(Claim.getInstance(), loc.add(0.5, Claim.getInstance().getConfig().getInt("settings.hologram.hologram-height"), 0.5));
        for (String str : Claim.getInstance().getConfig().getStringList("settings.hologram.hologram-lines")) {
            str = HexColor.hex(str);
            holo.appendTextLine(str
                    .replace("{owner}", owner)
                    .replace("{remaining-time}", ClaimManager.calcTime(data))
                    .replace("{team-size}", data.getMembers().size() + ""));
        }
        loc.setX(loc.getX() - 0.5);
        loc.setY(loc.getY() - Claim.getInstance().getConfig().getInt("settings.hologram.hologram-height") );
        loc.setZ(loc.getZ() - 0.5);
        data.setHolo(holo);
    }
    public void deleteHologram(@NotNull DataHandler data) {
        data.getHolo().delete();
    }

    public void updateHolograms() {
        for (DataHandler data : Claim.getInstance().getClaims()) {
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
            if (data.getDay() == 0) {
                if (data.getHour() == 0) {
                    if (data.getMinutes() == 0) {
                        if (data.getSeconds() == 0) {
                            Claim.getInstance().getClaimManager().deleteClaims(data);
                        }
                    }
                }
            }
            if (getServer().getOnlinePlayers().size() > 0) {
                data.getHolo().delete();
                Location loc = data.getBlockLocation();
                Hologram holo = HologramsAPI.createHologram(Claim.getInstance(), loc.add(0.5, Claim.getInstance().getConfig().getInt("settings.hologram.hologram-height"), 0.5));
                for (String str : Claim.getInstance().getConfig().getStringList("settings.hologram.hologram-lines")) {
                    str = HexColor.hex(str);
                    holo.appendTextLine(str
                           .replace("{owner}", data.getOwner())
                           .replace("{remaining-time}", ClaimManager.calcTime(data))
                           .replace("{team-size}", data.getMembers().size() + ""));
                }
                loc.setX(loc.getX() - 0.5);
                loc.setY(loc.getY() - Claim.getInstance().getConfig().getInt("settings.hologram.hologram-height"));
                loc.setZ(loc.getZ() - 0.5);
                data.setHolo(holo);
            }
        }
    }
}
