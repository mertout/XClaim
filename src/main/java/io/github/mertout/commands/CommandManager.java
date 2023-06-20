package io.github.mertout.commands;

import io.github.mertout.commands.impl.*;
import io.github.mertout.filemanager.files.MessagesFile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {

    private ArrayList<SubCommand> subcommands = new ArrayList<>();

    public CommandManager(){
        subcommands.add(new ReloadCommand());
        subcommands.add(new GiveCommand());
        subcommands.add(new BypassCommand());
    }

    @Override
    public boolean onCommand(CommandSender cs, Command command, String label, String[] args) {


        if (args.length > 0){
            for (int i = 0; i < getSubcommands().size(); i++){
                if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName()) && (getSubcommands().get(i).getSubName().equals("") || (args.length > 1 && getSubcommands().get(i).getSubName().equals(args[1])))) {
                    if (cs instanceof Player p && !p.hasPermission(getSubcommands().get(i).getPermission())) {
                        p.sendMessage(MessagesFile.convertString("messages.doesnt-permission"));
                        return false;
                    }
                    if (args.length == getSubcommands().get(i).getLength()) {
                        getSubcommands().get(i).perform(cs, args);
                    }
                    else {
                        cs.sendMessage(MessagesFile.convertString("messages.arg-error"));
                        return false;
                    }
                }
            }
        }
        else {
            if (cs instanceof Player p && !p.hasPermission("xclaim.help")) {
                p.sendMessage(MessagesFile.convertString("messages.doesnt-permission"));
                return false;
            }
            cs.sendMessage(ChatColor.GRAY + "--------------------------------");
            for (int i = 0; i < getSubcommands().size(); i++){
                cs.sendMessage(ChatColor.GOLD + getSubcommands().get(i).getSyntax() + ChatColor.GRAY +" - " + ChatColor.YELLOW + getSubcommands().get(i).getDescription());
            }
            cs.sendMessage(ChatColor.GRAY + "--------------------------------");
        }
        return true;
    }

    public ArrayList<SubCommand> getSubcommands(){
        return subcommands;
    }

}

