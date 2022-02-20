package io.github.mertout.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabComplete implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            return StringUtil.copyPartialMatches(strings[0], Arrays.asList(new String[]{"give", "reload"}), new ArrayList<>());
        }
        return null;
    }
}
