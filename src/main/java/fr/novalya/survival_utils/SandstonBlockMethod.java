package fr.novalya.survival_utils;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class SandstonBlockMethod extends BukkitRunnable implements Listener {
    private Block blockPlaced;

    public SandstonBlockMethod(Block blockPlaced) {
        this.blockPlaced = blockPlaced;
    }

    @Override
    public void run() {

        blockPlaced.setType(Material.AIR);
        blockPlaced.getWorld().spawnParticle(Particle.LAVA, blockPlaced.getLocation(), 10);

    }
}
