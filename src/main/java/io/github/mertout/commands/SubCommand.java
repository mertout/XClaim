package io.github.mertout.commands;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {

    public abstract String getName();

    public abstract String getSubName();

    public abstract String getDescription();

    public abstract String getSyntax();
    public abstract String getPermission();

    public abstract int getLength();

    public abstract void perform(CommandSender cs, String args[]);

}
