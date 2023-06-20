package io.github.mertout.hooks;

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
        return "1.5.2";
    }

    public boolean persist() {
        return true;
    }

    public String onPlaceholderRequest(@NotNull final Player p, @NotNull final String identifier) {
        ClaimManager claimManager = Claim.getInstance().getClaimManager();
        DataHandler chunk = claimManager.getChunkClaim(p.getLocation());
        switch (identifier) {
            case "owner":
                if (chunk != null) {
                    return chunk.getOwner().toString();
                }
                return "";
            case "team_size":
                if (chunk != null) {
                    return chunk.getMembers().size() + "";
                }
                return "";
            case "remaining_time":
                if (chunk != null) {
                    return ClaimManager.calcTime(chunk);
                }
                return "";
            case "status":
                if (chunk != null) {
                    return MessagesFile.convertString("messages.ownership.taken");
                }
                return MessagesFile.convertString("messages.ownership.untaken");
        }
        return null;
    }
}
