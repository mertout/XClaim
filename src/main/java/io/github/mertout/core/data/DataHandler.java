package io.github.mertout.core.data;

import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;

public class DataHandler
{
    private String chunk;
    private Location bloc;
    private String owner;
    private Integer day;
    private Integer hour;
    private Integer minutes;
    private Integer seconds;

    private Hologram hologram;

    private final List<String> members;

    private String creationdate;

    public DataHandler() {
        this.members = new ArrayList<String>();
    }

    public String getChunk() {
        return this.chunk;
    }

    public Location getBlockLocation() {
        return this.bloc;
    }

    public String getOwner() {
        return this.owner;
    }

    public Integer getDays() {
        return this.day;
    }

    public Integer getHours() {
        return this.hour;
    }

    public Integer getMinutes() {
        return this.minutes;
    }

    public Integer getSeconds() {
        return this.seconds;
    }

    public Hologram getHologram() {
        return this.hologram;
    }
    public List<String> getMembers() {
        return this.members;
    }

    public String getCreationDate() {
        return creationdate;
    }

    public void setChunk(@NotNull final String c) {
        this.chunk = c;
    }

    public void setBlockLocation(@NotNull final Location loc) {
        this.bloc = loc;
    }

    public void setOwner(@NotNull final String p) {
        this.owner = p;
    }

    public void setDay(@NotNull final Integer d) {
        this.day = d;
    }

    public void setHour(@NotNull final Integer h) {
        this.hour = h;
    }

    public void setMinutes(@NotNull final Integer m) {
        this.minutes = m;
    }

    public void setSeconds(@NotNull final Integer s) {
        this.seconds = s;
    }

    public void setHologram(@NotNull final Hologram h) {
        this.hologram = h;
    }
    public void addDay(@NotNull final Integer d) {
        this.day += d;
    }
    public void setCreationDate(@NotNull final String creationdate) {
        this.creationdate = creationdate;
    }
}