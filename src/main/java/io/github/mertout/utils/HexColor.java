package io.github.mertout.utils;

import java.util.regex.Matcher;
import org.bukkit.ChatColor;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

public class HexColor
{
    public static String hex(@NotNull String message) {
        final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        for (Matcher matcher = pattern.matcher(message); matcher.find(); matcher = pattern.matcher(message)) {
            final String hexCode = message.substring(matcher.start(), matcher.end());
            final String replaceSharp = hexCode.replace('#', 'x');
            final char[] ch = replaceSharp.toCharArray();
            final StringBuilder builder = new StringBuilder("");
            for (final char c : ch) {
                builder.append("&" + c);
            }
            message = message.replace(hexCode, builder.toString());
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
