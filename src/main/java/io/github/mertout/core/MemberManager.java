package io.github.mertout.core;

import io.github.mertout.Claim;
import io.github.mertout.api.events.ClaimMemberAddEvent;
import io.github.mertout.api.events.ClaimMemberRemoveEvent;
import io.github.mertout.core.data.DataHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MemberManager {

    public int getMemberSize(Player p) {
        int memberSize = 0;
        if (Claim.getInstance().getClaimManager().getPlayerClaim(p).getMembers().size() > 0) {
            memberSize = Claim.getInstance().getClaimManager().getPlayerClaim(p).getMembers().size();
        }
        return memberSize;
    }
    public int getMaxMemberSize(Player p) {
        int maxMembers = Claim.getInstance().getConfig().getInt("settings.default-max-member-in-claim");
        for (int i = 100; i >= 1; i--) {
            if (p.hasPermission("xclaim.max.member." + i)) {
                maxMembers = i;
                break;
            }
        }
        return maxMembers;
    }
    public boolean canAddAnyMember(Player p) {
        return (getMaxMemberSize(p) > getMemberSize(p));
    }
    public void addMemberToClaim(Player p, DataHandler claim, Player p2) {
        Bukkit.getScheduler().runTask(Claim.getInstance(), () -> {
            ClaimMemberAddEvent event = new ClaimMemberAddEvent(claim, p, p2);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return;
            }
            claim.getMembers().add(p2.getName());
        });
    }
    public void removeMemberFromClaim(Player p, DataHandler claim, Player p2) {
        ClaimMemberRemoveEvent event = new ClaimMemberRemoveEvent(claim, p, p2);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        claim.getMembers().remove(p2.getName());
    }
}
