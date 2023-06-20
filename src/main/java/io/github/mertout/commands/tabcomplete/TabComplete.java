package io.github.mertout.commands.tabcomplete;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import java.util.Collection;
import org.bukkit.util.StringUtil;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.jetbrains.annotations.NotNull;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabComplete implements TabCompleter {
    @Nullable
    public List<String> onTabComplete(@NotNull final CommandSender cs, @NotNull final Command cmd, @NotNull final String s, @NotNull final String[] strings) {
        if (strings.length == 1) {
            ArrayList<String> list = new ArrayList<>();
            if (cs.hasPermission("xclaim.give")) { list.add("give"); }
            if (cs.hasPermission("xclaim.reload")) { list.add("reload"); }
            if (cs.hasPermission("xclaim.bypass")) { list.add("bypass"); }
            return (List<String>)StringUtil.copyPartialMatches(strings[0], list, (Collection)new ArrayList());
        }
        else if (strings.length == 2) {
            ArrayList<String> list = new ArrayList<>();
            if (strings[0].equalsIgnoreCase("give")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    list.add(p.getName());
                }
            }
            return (List<String>)StringUtil.copyPartialMatches(strings[1], list, (Collection)new ArrayList());
        }
        return null;
    }
}
