package io.github.mertout.commands.impl;

import io.github.mertout.Claim;
import io.github.mertout.commands.SubCommand;
import io.github.mertout.core.backup.timer.BackupTimer;
import io.github.mertout.core.timer.CreateTimer;
import io.github.mertout.core.timer.DataTimer;
import io.github.mertout.core.timer.MoveTimer;
import io.github.mertout.filemanager.files.MenusFile;
import io.github.mertout.filemanager.files.MessagesFile;
import io.github.mertout.holograms.file.HologramFile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand extends SubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getSubName() {
        return "";
    }

    @Override
    public String getDescription() {
        return "Reload files command!";
    }

    @Override
    public String getSyntax() {
        return "/xclaim reload";
    }

    @Override
    public String getPermission() {
        return "xclaim.reload";
    }

    @Override
    public int getLength() {
        return 1;
    }

    @Override
    public void perform(CommandSender cs, String[] strings) {
        Claim.getInstance().reloadConfig();
        if (Claim.getInstance().getConfig().getInt("settings.data-backup-time") > -1) {
            new BackupTimer();
        }
        if (Claim.getInstance().getConfig().getInt("settings.data-save-time") > -1) {
            new DataTimer();
        }
        if (Claim.getInstance().getConfig().getInt("settings.move-block-cooldown") > -1) {
            new MoveTimer();
        }
        if (Claim.getInstance().getConfig().getInt("settings.claim-create-cooldown") > -1) {
            new CreateTimer();
        }
        MessagesFile.reloadMessages();
        MenusFile.reloadMenusFiles();
        HologramFile.reloadHologramsFile();
        cs.sendMessage(MessagesFile.convertString("messages.reloaded-files"));

    }
}
