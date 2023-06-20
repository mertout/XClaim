package io.github.mertout.borders;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

public class ClaimBorder {

    public void setIndividualBorder(Player player) {
        WorldBorder border = player.getWorld().getWorldBorder();
        Chunk chunk = player.getLocation().getChunk();
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();
        int centerX = (chunkX << 4) + 8;
        int centerZ = (chunkZ << 4) + 8;
        int centerY = player.getWorld().getHighestBlockYAt(centerX, centerZ);

        Location center = new Location(player.getWorld(), centerX, centerY, centerZ);
        border.setCenter(center);
        border.setSize(16);
    }

    public void resetBorder(Player player) {
        player.getWorld().getWorldBorder().reset();
    }
}
