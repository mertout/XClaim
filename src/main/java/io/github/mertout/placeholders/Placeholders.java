package io.github.mertout.placeholders;

import io.github.mertout.Claim;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Placeholders extends PlaceholderExpansion {

    public @NotNull String getAuthor() {
        return "mert.out";
    }

    public @NotNull String getIdentifier() {
        return "xclaim";
    }

    public @NotNull String getVersion() {
        return "1.0.0";
    }

    public boolean persist() {
        return true;
    }

    public String onPlaceholderRequest(Player p, @NotNull String identifier) {
        switch(identifier) {
            case "owner":
                if (Claim.getInstance().getClaimManager().hasClaim(p)) {
                    if (Claim.getInstance().getClaimManager().PlayerToClaim(p) != null) {
                        return Claim.getInstance().getClaimManager().PlayerToClaim(p).getOwner();
                    }
                    else {
                        return "";
                    }
                }
                else {
                    return "";
                }
            case "team-size":
                if (Claim.getInstance().getClaimManager().hasClaim(p)) {
                    if (Claim.getInstance().getClaimManager().PlayerToClaim(p) != null) {
                        return Claim.getInstance().getClaimManager().PlayerToClaim(p).getMembers().size() + "";
                    }
                    else {
                        return "";
                    }
                }
                else {
                    return "";
                }
            case "owner-location":
                if (Claim.getInstance().getClaimManager().getChunk(p.getLocation()) != null) {
                    return Claim.getInstance().getClaimManager().getChunk(p.getLocation()).getOwner();
                }
                else {
                    return "";
                }
            case "team-size-locationn":
                if (Claim.getInstance().getClaimManager().getChunk(p.getLocation()) != null) {
                    return Claim.getInstance().getClaimManager().getChunk(p.getLocation()).getMembers().size() + "";
                }
                else {
                    return "";
                }
        }
        return null;
    }
}
