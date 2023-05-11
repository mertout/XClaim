package io.github.mertout.api;

import io.github.mertout.Claim;
import io.github.mertout.core.ClaimManager;
import io.github.mertout.core.data.DataHandler;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ClaimAPI {

    public static boolean isHasClaim(@NotNull Player p) {
        return Claim.getInstance().getClaimManager().hasClaim(p);
    }

    public static boolean isMemberAnyClaim(@NotNull Player p) {
        return Claim.getInstance().getClaimManager().getPlayerClaim(p) != null;
    }

    public static DataHandler getClaim(@NotNull Player p) {
        return Claim.getInstance().getClaimManager().getPlayerClaim(p);
    }

    public static String getClaimRemaining(@NotNull DataHandler data) {
        return ClaimManager.calcTime(data);
    }

    public static List<String> getClaimMembers(@NotNull DataHandler data) {
        return data.getMembers();
    }

    public static Location getClaimBlockLocation(@NotNull DataHandler data) {
        return data.getBlockLocation();
    }

    public static String getClaimOwner(@NotNull DataHandler data) {
        return data.getOwner();
    }

    public static String getChunk(@NotNull DataHandler data) {
        return data.getChunk();
    }

    public static boolean isClaimable(@NotNull Location loc) {
        return Claim.getInstance().getClaimManager().getChunkClaim(loc) == null;
    }

    public static boolean isClaimableForPlayer(@NotNull Location loc, @NotNull Player p) {
        return !Claim.getInstance().getClaimManager().hasClaim(p) && Claim.getInstance().getClaimManager().getChunkClaim(p.getLocation()) == null;
    }

    public static boolean hasClaimAtLocation(@NotNull Location loc, @NotNull Player p) {
        return Claim.getInstance().getClaimManager().hasClaimAtLocation(loc, p);
    }
}
