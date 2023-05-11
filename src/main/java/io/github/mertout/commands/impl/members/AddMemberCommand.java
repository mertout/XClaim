package io.github.mertout.commands.impl.members;

import io.github.mertout.Claim;
import io.github.mertout.commands.SubCommand;
import io.github.mertout.filemanager.files.MessagesFile;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddMemberCommand extends SubCommand {

    @Override
    public String getName() {
        return "member";
    }

    @Override
    public String getSubName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "Add any member to claim command!";
    }

    @Override
    public String getSyntax() {
        return "/xclaim member add <player>";
    }

    @Override
    public String getPermission() {
        return "xclaim.member.add";
    }

    @Override
    public int getLength() {
        return 4;
    }

    @Override
    public void perform(CommandSender cs, String[] strings) {
        if (Bukkit.getPlayer(strings[2]) == null) {
            cs.sendMessage(MessagesFile.convertString("messages.player-is-not-online").replace("{player}", strings[2]));
            return;
        }
        if (Bukkit.getPlayer(strings[3]) == null) {
            cs.sendMessage(MessagesFile.convertString("messages.player-is-not-online").replace("{player}", strings[3]));
            return;
        }
        Player target = Bukkit.getPlayer(strings[1]);
        Player target2 = Bukkit.getPlayer(strings[2]);
        if (!Claim.getInstance().getClaimManager().hasClaim(target2)) {
            cs.sendMessage(MessagesFile.convertString("messages.admin.player-has-not-claim").replace("{player}", strings[3]));
            return;
        }
        if (!Claim.getInstance().getMemberManager().canAddAnyMember(target2)) {
            cs.sendMessage(MessagesFile.convertString("messages.admin.claim-is-full").replace("{player}", strings[3]));
            return;
        }
        if (Claim.getInstance().getClaimManager().getPlayerClaim(target2).getMembers().contains(strings[1])) {
            cs.sendMessage(MessagesFile.convertString("messages.admin.member-already-in-claim")
                    .replace("{player}", strings[2])
                    .replace("{target}", strings[3]));
            return;
        }
        Claim.getInstance().getMemberManager().addMemberToClaim(target2, Claim.getInstance().getClaimManager().getPlayerClaim(target2), target);
        cs.sendMessage(MessagesFile.convertString("messages.admin.claim-member-added")
                .replace("{player}", strings[2])
                .replace("{target}", strings[3]));
    }
}
