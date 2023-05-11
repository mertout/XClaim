package io.github.mertout.commands;

import io.github.mertout.commands.impl.BypassCommand;
import io.github.mertout.commands.impl.DeleteCommand;
import io.github.mertout.commands.impl.GiveCommand;
import io.github.mertout.commands.impl.ReloadCommand;
import io.github.mertout.commands.impl.days.AddDayCommand;
import io.github.mertout.commands.impl.days.RemoveDayCommand;
import io.github.mertout.commands.impl.days.SetDayCommand;
import io.github.mertout.commands.impl.members.AddMemberCommand;
import io.github.mertout.commands.impl.members.RemoveMemberCommand;
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
        subcommands.add(new DeleteCommand());
        subcommands.add(new BypassCommand());
        subcommands.add(new AddMemberCommand());
        subcommands.add(new RemoveMemberCommand());
        subcommands.add(new AddDayCommand());
        subcommands.add(new RemoveDayCommand());
        subcommands.add(new SetDayCommand());
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

