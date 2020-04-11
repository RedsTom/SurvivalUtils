package fr.novalya.survival_utils.listeners;

import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class DeathListener extends CustomListener<EntityDeathEvent>{
    @Override
    public void run(EntityDeathEvent event) {

        List<EntityType> membraneDroppers = Lists.newArrayList(EntityType.ZOMBIE, EntityType.SKELETON, EntityType.CREEPER, EntityType.ZOMBIE_VILLAGER, EntityType.PHANTOM);

        if(membraneDroppers.contains(event.getEntity().getType())){

            int random = new Random().nextInt(500);

            if(random == 250){

                event.getDrops().add(new ItemStack(Material.PHANTOM_MEMBRANE));

            }

        }

    }
}
