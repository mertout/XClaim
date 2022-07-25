package io.github.mertout.placeholders;

import io.github.mertout.Claim;
import io.github.mertout.core.ClaimManager;
import io.github.mertout.filemanager.MessagesFile;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class Placeholders extends PlaceholderExpansion
{
    @NotNull
    public String getAuthor() {
        return "mert.out";
    }
    
    @NotNull
    public String getIdentifier() {
        return "xclaim";
    }
    
    @NotNull
    public String getVersion() {
        return "1.0.2";
    }
    
    public boolean persist() {
        return true;
    }
    
    public String onPlaceholderRequest(final Player p, @NotNull final String identifier) {
        switch (identifier) {
            case "owner":
                if (Claim.getInstance().getClaimManager().PlayerToClaim2(p) != null) {
                    return Claim.getInstance().getClaimManager().PlayerToClaim2(p).getOwner();
                }
                return "";
            case "team_size":
                if (Claim.getInstance().getClaimManager().PlayerToClaim2(p) != null) {
                    return Claim.getInstance().getClaimManager().PlayerToClaim2(p).getMembers().size() + "";
                }
                return "";
            case "owner_via_location":
                if (Claim.getInstance().getClaimManager().getChunk(p.getLocation()) != null) {
                    return Claim.getInstance().getClaimManager().getChunk(p.getLocation()).getOwner();
                }
                return "";
            case "team_size_via_location":
                if (Claim.getInstance().getClaimManager().getChunk(p.getLocation()) != null) {
                    return Claim.getInstance().getClaimManager().getChunk(p.getLocation()).getMembers().size() + "";
                }
                return "";
            case "remaining":
                if (Claim.getInstance().getClaimManager().PlayerToClaim2(p) != null) {
                    return ClaimManager.calcTime(Claim.getInstance().getClaimManager().PlayerToClaim2(p));
                }
                return "";
            case "remaining_via_location":
                if (Claim.getInstance().getClaimManager().getChunk(p.getLocation()) != null) {
                    return ClaimManager.calcTime(Claim.getInstance().getClaimManager().getChunk(p.getLocation()));
                }
                return "";
            case "ownership_via_location":
                if (Claim.getInstance().getClaimManager().getChunk(p.getLocation()) != null) {
                    return MessagesFile.convertString("messages.ownership.taken");
                }
                return MessagesFile.convertString("messages.ownership.untaken");
        }
        return null;
    }
}
