package io.github.mertout.commands.impl;

import io.github.mertout.Claim;
import io.github.mertout.commands.SubCommand;
import io.github.mertout.filemanager.files.MessagesFile;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeleteCommand extends SubCommand {

    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getSubName() {
        return "";
    }

    @Override
    public String getDescription() {
        return "Delete claim command!";
    }

    @Override
    public String getSyntax() {
        return "/xclaim delete <player>";
    }

    @Override
    public String getPermission() {
        return "xclaim.delete";
    }

    @Override
    public int getLength() {
        return 2;
    }

    @Override
    public void perform(CommandSender cs, String[] strings) {
        if (Bukkit.getPlayer(strings[1]) == null) {
            cs.sendMessage(MessagesFile.convertString("messages.player-is-not-online").replace("{player}", strings[1]));
            return;
        }
        Player target = Bukkit.getPlayer(strings[1]);
        if (!Claim.getInstance().getClaimManager().hasClaim(target)) {
            cs.sendMessage(MessagesFile.convertString("messages.admin.player-has-not-claim").replace("{player}", strings[1]));
            return;
        }
        Claim.getInstance().getClaimManager().deleteClaim(target);
        cs.sendMessage(MessagesFile.convertString("messages.admin.claim-deleted").replace("{player}", strings[1]));
    }
}
