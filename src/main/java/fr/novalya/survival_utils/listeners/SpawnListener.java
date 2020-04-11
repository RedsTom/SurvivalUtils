package fr.novalya.survival_utils.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntitySpawnEvent;

public class SpawnListener extends CustomListener<EntitySpawnEvent> {
    @Override
    public void run(EntitySpawnEvent event) {

        if(event.getEntityType() == EntityType.PHANTOM){
            event.setCancelled(true);
        }

    }
}
