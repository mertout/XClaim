package io.github.mertout.commands.impl.days;

import io.github.mertout.Claim;
import io.github.mertout.commands.SubCommand;
import io.github.mertout.core.data.DataHandler;
import io.github.mertout.filemanager.files.MessagesFile;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddDayCommand extends SubCommand {

    @Override
    public String getName() {
        return "day";
    }

    @Override
    public String getSubName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "Add any member to claim command!";
    }

    @Override
    public String getSyntax() {
        return "/xclaim day add <player> <day>";
    }

    @Override
    public String getPermission() {
        return "xclaim.day.add";
    }

    @Override
    public int getLength() {
        return 4;
    }

    @Override
    public void perform(CommandSender cs, String[] strings) {
        if (Bukkit.getPlayer(strings[2]) == null) {
            cs.sendMessage(MessagesFile.convertString("messages.player-is-not-online")
                    .replace("{player}", strings[2]));
            return;
        }
        Player target = Bukkit.getPlayer(strings[2]);
        if (!Claim.getInstance().getClaimManager().hasClaim(target)) {
            cs.sendMessage(MessagesFile.convertString("messages.admin.player-has-not-claim")
                    .replace("{player}", strings[2]));
            return;
        }
        if (Integer.parseInt(strings[3]) < 1) {
            cs.sendMessage(MessagesFile.convertString("messages.admin.invalid-number")
                    .replace("{player}", strings[2])
                    .replace("{number}", strings[3]));
            return;
        }
        DataHandler claim = Claim.getInstance().getClaimManager().getPlayerClaim(target);
        claim.setDay((claim.getDay() + Integer.parseInt(strings[3])));
        cs.sendMessage(MessagesFile.convertString("messages.admin.claim-add-day")
                .replace("{target}", strings[2])
                .replace("{added-day}", strings[3])
                .replace("{total-day}", claim.getDay() + ""));
    }
}
