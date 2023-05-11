package io.github.mertout.commands.impl;

import io.github.mertout.Claim;
import io.github.mertout.commands.SubCommand;
import io.github.mertout.filemanager.files.MessagesFile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BypassCommand extends SubCommand {

    @Override
    public String getName() {
        return "bypass";
    }

    @Override
    public String getSubName() {
        return "";
    }

    @Override
    public String getDescription() {
        return "Bypass all claim protection command!";
    }

    @Override
    public String getSyntax() {
        return "/xclaim bypass";
    }

    @Override
    public String getPermission() {
        return "xclaim.bypass";
    }

    @Override
    public int getLength() {
        return 1;
    }

    @Override
    public void perform(CommandSender cs, String[] strings) {
        if (!(cs instanceof Player)) {
            cs.sendMessage(MessagesFile.convertString("messages.only-player"));
            return;
        }
        Player p = (Player) cs;
        if (Claim.getInstance().getAdminBypassList().contains(p)) {
            p.sendMessage(MessagesFile.convertString("messages.admin.player-bypass-mode-off"));
            Claim.getInstance().getAdminBypassList().remove(p);
        }
        else {
            p.sendMessage(MessagesFile.convertString("messages.admin.player-bypass-mode-on"));
            Claim.getInstance().getAdminBypassList().add(p);
        }
    }
}
