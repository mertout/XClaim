package io.github.mertout.filemanager;

import io.github.mertout.utils.HexColor;
import org.jetbrains.annotations.NotNull;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.IOException;
import io.github.mertout.Claim;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;

public class MessagesFile
{
    public static FileConfiguration msgfc;
    public static File msgfile;

    public static void loadMessagesFiles() {
        MessagesFile.msgfile = new File(Claim.getInstance().getDataFolder() + "/lang", "messages-en_US.yml");
        if (!MessagesFile.msgfile.exists()) {
            try {
                MessagesFile.msgfile.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            MessagesFile.msgfc = YamlConfiguration.loadConfiguration(MessagesFile.msgfile);
            MessagesFile.msgfc.set("messages.prefix", "&6Claim &8»");
            MessagesFile.msgfc.set("messages.created-claims", "{prefix} &aSuccessfully created claim.");
            MessagesFile.msgfc.set("messages.already-have-claims", "{prefix} &cAlready have claims!");
            MessagesFile.msgfc.set("messages.member-adding", "{prefix} &aType the name of the member you want to add to the chat &7(&cType &6- &cto cancel&7).");
            MessagesFile.msgfc.set("messages.member-added", "{prefix} &aSuccessfully &e{player} &ato claim.");
            MessagesFile.msgfc.set("messages.deleted-claim", "{prefix} &aSuccessfully deleted claim.");
            MessagesFile.msgfc.set("messages.arg-error", "{prefix} &cArg error!");
            MessagesFile.msgfc.set("messages.doesnt-permission", "{prefix} &cDoesn't have permission!");
            MessagesFile.msgfc.set("messages.invalid-arg", "{prefix} &cInvalid arg!");
            MessagesFile.msgfc.set("messages.only-player", "Only players can use this command!");
            MessagesFile.msgfc.set("messages.claim-expired", "{prefix} &cClaim has expired!(&6x: {x}, y: {y}, z: {z}, world: {world}&c)");
            MessagesFile.msgfc.set("messages.add-error", "{prefix} &cCan't add yourself!");
            MessagesFile.msgfc.set("messages.no-player-in-claim", "{prefix} &cThere is no one in the claim!");
            MessagesFile.msgfc.set("messages.player-not-found", "{prefix} &cPlayer not found!");
            MessagesFile.msgfc.set("messages.reloaded-files", "{prefix} &aAll files have been reloaded!");
            MessagesFile.msgfc.set("messages.kicked-claim", "{prefix} &e{player} &awas kicked out of the claim!");
            MessagesFile.msgfc.set("messages.renewed-day", "{prefix} &aSuccessfully renewed claim days &7(&aTotal: &e{day}&ad, &e{hour}&ah, &e{minute}&am, &e{second}&as&7)");
            MessagesFile.msgfc.set("messages.dont-have-money", "{prefix} &cYou don't have enough money (&6Required: {money}&c)!");
            MessagesFile.msgfc.set("messages.disabled-commands-in-claim", "{prefix} &cThe &6/{command} &cis disabled in the claim!");
            MessagesFile.msgfc.set("messages.disabled-pvp-in-claim", "{prefix} &cPvp disabled in the claim!");
            MessagesFile.msgfc.set("messages.disabled-worlds", "{prefix} &cThe claim is disabled in the world you are in");
            MessagesFile.msgfc.set("messages.moved-claim-block", "{prefix} &aSuccessfully moved the claim block.");
            MessagesFile.msgfc.set("messages.move-claim-block-cooldown", "{prefix} &cMove the claim block again: you need to wait &6{hour} &chours, &6{minute} &cminutes and &6{second} &cseconds");
            MessagesFile.msgfc.set("messages.move-claim-block-error", "{prefix} &cTargeted chunk is not yours!");
            MessagesFile.msgfc.set("messages.member-add-canceled", "{prefix} &cAdding member has been canceled!");
            MessagesFile.msgfc.set("messages.player-is-not-online", "{prefix} &6{player} &cis not online!");
            MessagesFile.msgfc.set("messages.join-alerts.title", "&6&lCLAIM");
            MessagesFile.msgfc.set("messages.join-alerts.sub-title", "&fYou have entered &e{owner}'s &fclaim");
            MessagesFile.msgfc.set("messages.status.online", "&aOnline!");
            MessagesFile.msgfc.set("messages.status.offline", "&cOffline!");
            MessagesFile.msgfc.set("messages.ownership.taken", "&aOwned!");
            MessagesFile.msgfc.set("messages.ownership.untaken", "&aUnowned!");
            try {
                MessagesFile.msgfc.save(MessagesFile.msgfile);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        MessagesFile.msgfile = new File(Claim.getInstance().getDataFolder() + "/lang", "messages-tr_TR.yml");
        if (!MessagesFile.msgfile.exists()) {
            try {
                MessagesFile.msgfile.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            MessagesFile.msgfc = YamlConfiguration.loadConfiguration(MessagesFile.msgfile);
            MessagesFile.msgfc.set("messages.prefix", "&6Bölge &8»");
            MessagesFile.msgfc.set("messages.created-claims", "{prefix} &aBaşarıyla bölge oluşturdun.");
            MessagesFile.msgfc.set("messages.already-have-claims", "{prefix} &cZaten bir bölgen mevcut!");
            MessagesFile.msgfc.set("messages.member-adding", "{prefix} &aEklemek isteğin oyuncunun ismini sohbete yaz &7(&cİptal etmek için - yaz&7).");
            MessagesFile.msgfc.set("messages.member-added", "{prefix} &aBaşarıyla &e{player} &abölgeye ekledin.");
            MessagesFile.msgfc.set("messages.deleted-claim", "{prefix} &aBaşarıyla bölgeyi sildin.");
            MessagesFile.msgfc.set("messages.arg-error", "{prefix} &cYanlış kullanım!");
            MessagesFile.msgfc.set("messages.doesnt-permission", "{prefix} &cYetkin yok!");
            MessagesFile.msgfc.set("messages.invalid-arg", "{prefix} &cYanlış kullanım!");
            MessagesFile.msgfc.set("messages.only-player", "Bu komutu sadece oyuncular kullanabilir!");
            MessagesFile.msgfc.set("messages.claim-expired", "{prefix} &cBir bölgenin süresi doldu!(&6x: {x}, y: {y}, z: {z}, dünya: {world}&c)");
            MessagesFile.msgfc.set("messages.add-error", "{prefix} &cKendini ekleyemezsin!");
            MessagesFile.msgfc.set("messages.no-player-in-claim", "{prefix} &cBölgende hiç kimse yok :(");
            MessagesFile.msgfc.set("messages.player-not-found", "{prefix} &cOyuncu bulunamadı!");
            MessagesFile.msgfc.set("messages.reloaded-files", "{prefix} &aBütün dosyalar yenilendi yüklendi!");
            MessagesFile.msgfc.set("messages.kicked-claim", "{prefix} &e{player} &abölgeden atıldı!");
            MessagesFile.msgfc.set("messages.renewed-day", "{prefix} &aBaşarıyla bölgenin süresini yeniledin &7(&aGüncel bölge süresi: &e{day}&agü, &e{hour}&asa, &e{minute}&ada, &e{second}&asn&7)");
            MessagesFile.msgfc.set("messages.dont-have-money", "{prefix} &cYetersiz para (&6Gereken: {money}&c)!");
            MessagesFile.msgfc.set("messages.disabled-commands-in-claim", "{prefix} &6{command} &ckomutu bölgelerde kullanımı yasak!");
            MessagesFile.msgfc.set("messages.disabled-pvp-in-claim", "{prefix} &cBölgelerde Pvp kapalıdır!");
            MessagesFile.msgfc.set("messages.disabled-worlds", "{prefix} &cBu dünyada bölge oluşturamazsın!");
            MessagesFile.msgfc.set("messages.moved-claim-block", "{prefix} &aBaşarıyla bölge bloğunu taşıdın.");
            MessagesFile.msgfc.set("messages.move-claim-block-cooldown", "{prefix} &cBölge bloğunu tekrar taşımak için &6{hour} &csaat, &6{minute} &cdakika ve &6{second} &csaniye beklemelisin!");
            MessagesFile.msgfc.set("messages.move-claim-block-error", "{prefix} &cHedef chunk sana ait değil!");
            MessagesFile.msgfc.set("messages.member-add-canceled", "{prefix} &cÜye ekleme iptal edildi!");
            MessagesFile.msgfc.set("messages.player-is-not-online", "{prefix} &6{player} &cçevrimiçi değil!");
            MessagesFile.msgfc.set("messages.join-alerts.title", "&6&lBÖLGE");
            MessagesFile.msgfc.set("messages.join-alerts.sub-title", "&e{owner} &fadlı oyuncunun bölgesine giriş yaptın!");
            MessagesFile.msgfc.set("messages.status.online", "&aÇevrimiçi!");
            MessagesFile.msgfc.set("messages.status.offline", "&cÇevrimdışı!");
            MessagesFile.msgfc.set("messages.ownership.taken", "&aSahipli!");
            MessagesFile.msgfc.set("messages.ownership.untaken", "&aSahipsiz!");
            try {
                MessagesFile.msgfc.save(MessagesFile.msgfile);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        selectLang();
    }

    public static String convertString(@NotNull final String params1) {
        return HexColor.hex(MessagesFile.msgfc.getString(params1)).replaceAll("&", "§").replace("{prefix}", HexColor.hex(MessagesFile.msgfc.getString("messages.prefix")).replaceAll("&", "§"));
    }

    public static String convertStringCFG(@NotNull final String params1) {
        return HexColor.hex(Claim.getInstance().getConfig().getString(params1)).replaceAll("&", "§");
    }

    public static void reloadMessages() {
        selectLang();
        MessagesFile.msgfc = YamlConfiguration.loadConfiguration(MessagesFile.msgfile);
    }
    public static void selectLang() {
        if (Claim.getInstance().getConfig().getString("settings.lang").equals("en_US")) {
            MessagesFile.msgfile = new File(Claim.getInstance().getDataFolder() + "/lang", "messages-en_US.yml");
        }
        else {
            MessagesFile.msgfile = new File(Claim.getInstance().getDataFolder() + "/lang", "messages-tr_TR.yml");
        }
        msgfc = YamlConfiguration.loadConfiguration(msgfile);
    }
}
