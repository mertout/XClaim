package io.github.mertout.commands.impl;

import io.github.mertout.Claim;
import io.github.mertout.commands.SubCommand;
import io.github.mertout.core.data.task.DataTimer;
import io.github.mertout.filemanager.files.MenusFile;
import io.github.mertout.filemanager.files.MessagesFile;
import io.github.mertout.holograms.file.HologramFile;
import org.bukkit.command.CommandSender;

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
        if (Claim.getInstance().getConfig().getInt("settings.data-save-tick") > -1) {
            new DataTimer(Claim.getInstance().getConfig().getInt("settings.data-save-tick"));
        }
        MessagesFile.reloadMessages();
        MenusFile.reloadMenusFiles();
        HologramFile.reloadHologramsFile();
        cs.sendMessage(MessagesFile.convertString("messages.reloaded-files"));

    }
}
