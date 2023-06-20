package io.github.mertout.gui.backend;

import io.github.mertout.Claim;
import io.github.mertout.core.data.DataHandler;
import io.github.mertout.filemanager.files.MenusFile;
import io.github.mertout.filemanager.files.MessagesFile;
import io.github.mertout.gui.GuiCreator;
import io.github.mertout.gui.GuiType;
import io.github.mertout.utils.ClaimUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MenuClick {

    public MenuClick(@NotNull Player p, @NotNull int slot, @NotNull GuiType guiType) {
        ConfigurationSection sect = MenusFile.get(guiType).getConfigurationSection("gui.items");
        if (sect == null || guiType != GuiType.CLAIMMANAGEMENT) {
            return;
        }
        for (final String str : sect.getKeys(false))
            if (MenusFile.get(guiType).getInt("gui.items." + str + ".slot") == slot && MenusFile.get(guiType).isSet("gui.items." + str + ".action")) {
                final String action = MenusFile.get(guiType).getString("gui.items." + str + ".action");
                final String sw = action;
                final DataHandler data = Claim.getInstance().getClaimManager().getChunkClaim(p.getLocation());
                switch (sw) {
                    case "[Member-List]":
                        new GuiCreator(p, GuiType.MEMBERS);
                        break;
                    case "[Member-Add]":
                        Claim.getInstance().getAddList().put(p, data);
                        p.sendMessage(MessagesFile.convertString("messages.member-adding"));
                        break;
                    case "[Renew-Day]":
                        ClaimUtils.renewDay(p, data);
                        break;
                    case "[Delete-Claim]":
                        Claim.getInstance().getClaimManager().deleteClaim(p, data);
                        break;
                    case "[Move-Claim-Block]":
                        ClaimUtils.moveClaimBlock(p, data);
                        break;
                    case "[Look-Chunks]":
                        Claim.getInstance().getBorderCore().toggleBorder(p, data);
                        break;
            }
        }
    }
}
