package io.github.mertout.core.data;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class DataHandler {

    private String chunk;
    private Location bloc;
    private String owner;
    private Integer day;
    private Integer hour;
    private Integer minutes;
    private Integer seconds;
    private Hologram holo;
    private final List<String> members = new ArrayList<>();

    public String getChunk() {
        return chunk;
    }
    public Location getBlockLocation() {
        return bloc;
    }
    public String getOwner() {
        return owner;
    }
    public Integer getDay() {
        return day;
    }
    public Integer getHour() {
        return hour;
    }
    public Integer getMinutes() {
        return minutes;
    }
    public Integer getSeconds() {
        return seconds;
    }
    public Hologram getHolo() {
        return holo;
    }
    public List<String> getMembers() {
        return members;
    }
    public void setChunk(@NotNull String c) {
       chunk = c;
    }
    public void setBlockLocation(@NotNull Location loc) {
        bloc = loc;
    }
    public void setOwner(@NotNull String p) {
        owner = p;
    }
    public void setDay(@NotNull Integer d) {
        day = d;
    }
    public void setHour(@NotNull Integer h) {
        hour = h;
    }
    public void setMinutes(@NotNull Integer m) {
        minutes = m;
    }
    public void setSeconds(@NotNull Integer s) {
        seconds = s;
    }
    public void setHolo(@NotNull Hologram h) {
        holo = h;
    }
    public void addDay(@NotNull Integer d) {
        day = day + d;
    }
}
