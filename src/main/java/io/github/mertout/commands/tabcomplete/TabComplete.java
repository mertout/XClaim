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
            if (cs.hasPermission("xclaim.delete")) { list.add("delete"); }
            if (cs.hasPermission("xclaim.member.add") || cs.hasPermission("xclaim.member.remove")) { list.add("member"); }
            if (cs.hasPermission("xclaim.day.set") || cs.hasPermission("xclaim.day.add") || cs.hasPermission("xclaim.day.remove")) {list.add("day"); }
            return (List<String>)StringUtil.copyPartialMatches(strings[0], list, (Collection)new ArrayList());
        }
        else if (strings.length == 2) {
            ArrayList<String> list = new ArrayList<>();
            if (strings[0].equalsIgnoreCase("give") || strings[0].equalsIgnoreCase("delete")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    list.add(p.getName());
                }
            }
            else if (strings[0].equalsIgnoreCase("member")) {
                if (cs.hasPermission("xclaim.member.add")) { list.add("add"); }
                if (cs.hasPermission("xclaim.member.remove")) { list.add("remove"); }
            }
            else if (strings[0].equalsIgnoreCase("day")) {
                if (cs.hasPermission("xclaim.day.set")) { list.add("set"); }
                if (cs.hasPermission("xclaim.day.add")) { list.add("add"); }
                if (cs.hasPermission("xclaim.day.remove")) { list.add("remove"); }
            }
            return (List<String>)StringUtil.copyPartialMatches(strings[1], list, (Collection)new ArrayList());
        }
        else if (strings.length == 3) {
            if (strings[0].equalsIgnoreCase("member")) {
                ArrayList<String> list = new ArrayList<>();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    list.add(p.getName());
                }
                return StringUtil.copyPartialMatches(strings[2], list, new ArrayList<>());
            }
            else if (strings[0].equalsIgnoreCase("day")) {
                List<Number> list = new ArrayList<>();
                for (int i = 1; i < 100; i++) {
                    list.add(i);
                }
                List<String> stringList = new ArrayList<>();
                for (Number number : list) {
                    stringList.add(String.valueOf(number));
                }
                return StringUtil.copyPartialMatches(strings[2], stringList, new ArrayList<>());
            }
        }
        return null;
    }
}
