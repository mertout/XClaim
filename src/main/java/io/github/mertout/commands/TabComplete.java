package io.github.mertout.commands;

import org.jetbrains.annotations.Nullable;
import java.util.Collection;
import org.bukkit.util.StringUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.command.Command;
import org.jetbrains.annotations.NotNull;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabComplete implements TabCompleter
{
    @Nullable
    public List<String> onTabComplete(@NotNull final CommandSender cs, @NotNull final Command cmd, @NotNull final String s, @NotNull final String[] strings) {
        if (strings.length == 1) {
            if (cs.hasPermission("xclaim.give") && cs.hasPermission("xclaim.reload")) {
                return (List<String>)StringUtil.copyPartialMatches(strings[0], Arrays.asList("give", "reload"), (Collection)new ArrayList());
            }
            else if (cs.hasPermission("xclaim.give")) {
                return (List<String>)StringUtil.copyPartialMatches(strings[0], Arrays.asList("give"), (Collection)new ArrayList());
            }
            else if (cs.hasPermission("xclaim.reload")) {
                return (List<String>)StringUtil.copyPartialMatches(strings[0], Arrays.asList("reload"), (Collection)new ArrayList());
            }
            else if (cs.hasPermission("xclaim.reload")) {
                return null;
            }
        }
        return null;
    }
}
