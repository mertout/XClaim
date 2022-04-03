package io.github.mertout.filemanager;

import io.github.mertout.Claim;
import io.github.mertout.utils.HexColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class MessagesFile {

    public static FileConfiguration msgfc;
    public static File msgfile;

    public static void loadMessagesFiles() {
        msgfile = new File(Claim.getInstance().getDataFolder(), "messages.yml");
        if (!msgfile.exists()) {
            try {
                msgfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            msgfc = YamlConfiguration.loadConfiguration(msgfile);
            msgfc.set("messages.already-have-claims", "&cAlready have claim!");
            msgfc.set("messages.created-claims", "&aSuccessfully created claim.");
            msgfc.set("messages.member-adding", "&aWrite the name of the member you want to add to the chat. &7(&cType &6- &cto cancel.&7)");
            msgfc.set("messages.member-added", "&aSuccessfully &e{player} &ato claim.");
            msgfc.set("messages.deleted-claim", "&aSuccessfully deleted claim.");
            msgfc.set("messages.arg-error", "&cArg error!");
            msgfc.set("messages.doesnt-permission", "&cDoesn't have permission!");
            msgfc.set("messages.arg-error", "&cArg error!");
            msgfc.set("messages.invalid-arg", "&cInvalid arg!");
            msgfc.set("messages.only-player", "Only player!");
            msgfc.set("messages.claim-expired", "&cClaim has expired!(&6x: {x},y: {y},z: {z},world:{world}&c)");
            msgfc.set("messages.add-error", "&cCan't add yourself!");
            msgfc.set("messages.no-player-in-claim", "&cThere is no one in the claim!");
            msgfc.set("messages.player-not-found", "&cPlayer not found!");
            msgfc.set("messages.reloaded-files", "&aAll files have been reloaded");
            msgfc.set("messages.kicked-claim", "&e{player} &awas kicked out of the claim");
            msgfc.set("messages.renewed-day", "&aSuccessfully renewed claim days &7(&aTotal: &e{day}&ad, &e{hour}&ah, &e{minute}&am, &e{second}&as&7)");
            msgfc.set("messages.dont-have-money", "&cYou don't have enough money!");
            msgfc.set("messages.disabled-commands-in-claim", "&cThe &6{command} &cis disabled in the claim!");
            msgfc.set("messages.disabled-pvp-in-claim", "&cPvp disabled in the claim!");
            msgfc.set("messages.disabled-worlds", "&cThe claim is disabled in the world you are in");
            msgfc.set("messages.moved-claim-block", "&aSuccessfully moved claim block.");
            msgfc.set("messages.move-claim-block-cooldown", "&cMove the claim block again: you need to wait &6{hour} &chour, &6{minute} &cminute and &6{second} &csecond");
            msgfc.set("messages.move-claim-block-error", "&cThe chunk you found is not yours!");
            msgfc.set("messages.friends-add-canceled", "&cAdding friends has been canceled");
            try {
                msgfc.save(msgfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        msgfc = YamlConfiguration.loadConfiguration(msgfile);
    }
    public static String convertString(@NotNull String params1) {
        return HexColor.hex(msgfc.getString(params1));
    }
    public static void reloadMessages() {
        msgfc = YamlConfiguration.loadConfiguration(msgfile);
    }
}
