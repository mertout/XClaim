package io.github.mertout.borders;

import io.github.mertout.Claim;
import io.github.mertout.core.data.DataHandler;
import io.github.mertout.filemanager.files.MessagesFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class BorderCore {

    public HashMap<Player, Boolean> caches = new HashMap<>();

    public void toggleBorder(@NotNull Player p, @NotNull DataHandler data) {
        if (data == null) {
            return;
        }
        int sec = Claim.getInstance().getConfig().getInt("settings.look-chunk-seconds");
        if (!caches.containsKey(p)) {
            Claim.getInstance().getBorderTimer().addTimer(p);
            Claim.getInstance().getClaimBorder().setIndividualBorder(p);
            p.sendMessage(MessagesFile.convertString("messages.look-chunk-active").replace("{seconds}", sec + ""));
        }
        else {
            Claim.getInstance().getBorderTimer().removeTimer(p);
            Claim.getInstance().getClaimBorder().resetBorder(p);
            p.sendMessage(MessagesFile.convertString("messages.look-chunk-deactive").replace("{seconds}", sec + ""));
        }
    }
}
