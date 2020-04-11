package fr.novalya.survival_utils.listeners;

import org.bukkit.event.player.PlayerDropItemEvent;

public class DropListener extends CustomListener<PlayerDropItemEvent> {

    @Override
    public void run(PlayerDropItemEvent event) {

        if(event.getPlayer().getWorld().getName().equalsIgnoreCase("pvp")) event.setCancelled(true);

    }
}
