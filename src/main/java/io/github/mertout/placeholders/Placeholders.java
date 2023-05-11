package io.github.mertout.placeholders;

import io.github.mertout.Claim;
import io.github.mertout.core.ClaimManager;
import io.github.mertout.core.data.DataHandler;
import io.github.mertout.filemanager.files.MessagesFile;
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
        return "1.3";
    }
    
    public boolean persist() {
        return true;
    }

    public String onPlaceholderRequest(final Player p, @NotNull final String identifier) {
        ClaimManager claimManager = Claim.getInstance().getClaimManager();
        DataHandler claim = claimManager.getPlayerClaim(p);
        DataHandler chunk = claimManager.getChunkClaim(p.getLocation());
        switch (identifier) {
            case "owner":
                if (claim != null) {
                    return claim.getOwner().toString();
                }
                return "";
            case "team_size":
                if (claim != null) {
                    return claim.getMembers().size() + "";
                }
                return "";
            case "owner_via_location":
                if (chunk != null) {
                    return chunk.getOwner().toString();
                }
                return "";
            case "team_size_via_location":
                if (chunk != null) {
                    return chunk.getMembers().size() + "";
                }
                return "";
            case "remaining":
                if (claim != null) {
                    return ClaimManager.calcTime(claim);
                }
                return "";
            case "remaining_via_location":
                if (chunk != null) {
                    return ClaimManager.calcTime(chunk);
                }
                return "";
            case "ownership_via_location":
                if (chunk != null) {
                    return MessagesFile.convertString("messages.ownership.taken");
                }
                return MessagesFile.convertString("messages.ownership.untaken");
        }
        return null;
    }
}
