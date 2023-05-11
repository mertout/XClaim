package io.github.mertout.utils;

import io.github.mertout.Claim;
import io.github.mertout.api.events.ClaimBlockMoveEvent;
import io.github.mertout.api.events.ClaimDayRenewEvent;
import io.github.mertout.core.data.DataHandler;
import io.github.mertout.core.timer.MoveTimer;
import io.github.mertout.filemanager.files.MessagesFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClaimUtils {

    public static String checkStatus(String params) {
        if (Bukkit.getPlayer(params) != null) {
            return HexColor.hex(MessagesFile.convertString("messages.status.online"));
        }
        return HexColor.hex(MessagesFile.convertString("messages.status.offline"));
    }
    public static void memberKick(@NotNull Player p, @NotNull String owner) {
        DataHandler data = Claim.getInstance().getClaimManager().getPlayerClaim(p);
        if (data.getMembers().contains(owner)) {
            data.getMembers().remove(owner);
            p.getOpenInventory().close();
            p.sendMessage(MessagesFile.convertString("messages.kicked-claim").replace("{player}", owner));
        }
    }

    public static void renewDay(@NotNull Player p) {
        if (Claim.getInstance().getClaimManager().hasClaim(p)) {
            if (!(Claim.getInstance().getEconomy().getBalance(p) >= Claim.getInstance().getConfig().getInt("settings.renew-day-cost"))) {
                p.sendMessage(MessagesFile.convertString("messages.dont-have-money").replace("{money}", Claim.getInstance().getConfig().getString("settings.renew-day-cost")));
                return;
            }
            DataHandler data = Claim.getInstance().getClaimManager().getPlayerClaim(p);
            ClaimDayRenewEvent event = new ClaimDayRenewEvent(p, data);
            Bukkit.getServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                Claim.getInstance().getEconomy().withdrawPlayer(p, Claim.getInstance().getConfig().getInt("settings.renew-day-cost"));
                data.addDay(Claim.getInstance().getConfig().getInt("settings.claim-day"));
                p.sendMessage(MessagesFile.convertString("messages.renewed-day").replace("{day}", data.getDay() + "").replace("{hour}", data.getHour() + "").replace("{minute}", data.getMinutes() + "").replace("{second}", data.getSeconds() + ""));
            }
        }
    }
    public static void moveClaimBlock(final Player p) {
        if (!Claim.getInstance().getClaimManager().hasClaim(p)) {
            p.sendMessage(MessagesFile.convertString("messages.move-claim-block-error"));
            return;
        }
        final DataHandler data = Claim.getInstance().getClaimManager().getPlayerClaim(p);
        if (!p.getLocation().getChunk().toString().equals(data.getChunk())) {
            p.sendMessage(MessagesFile.convertString("messages.move-claim-block-error"));
            return;
        }
        if (MoveTimer.getMoveTask().containsKey(p)) {
            int hour = 0;
            int minute = 0;
            int second;
            for (second = MoveTimer.getMoveTask().get(p); second > 3600; second -= 3600, ++hour) {}
            while (second > 60) {
                second -= 60;
                ++minute;
            }
            p.sendMessage(MessagesFile.convertString("messages.move-claim-block-cooldown").replace("{hour}", hour + "").replace("{minute}", minute + "").replace("{second}", second + ""));
            return;
        }
        final ClaimBlockMoveEvent event = new ClaimBlockMoveEvent(p, data);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            Claim.getInstance().getHologramCore().moveHologram(data, p.getLocation().getBlock().getLocation());
            final Location loc = data.getBlockLocation();
            loc.getBlock().setType(Material.AIR);
            data.setBlockLocation(p.getLocation().getBlock().getLocation());
            p.getLocation().getBlock().setType(Material.matchMaterial(Claim.getInstance().getConfig().getString("settings.claim-block.material")));
            Claim.getInstance().getMoveTimer().addMoveCooldown(p);
            p.sendMessage(MessagesFile.convertString("messages.moved-claim-block"));
        }
    }
}
