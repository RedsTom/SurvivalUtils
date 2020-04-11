package fr.novalya.survival_utils.listeners;

import com.google.common.collect.Lists;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class DamageListener extends CustomListener<EntityDamageEvent>{
    @Override
    public void run(EntityDamageEvent event) {

        if(!(event.getEntity() instanceof Player)) return;

        List<EntityDamageEvent.DamageCause> causes = Lists.newArrayList(
                EntityDamageEvent.DamageCause.FALL,
                EntityDamageEvent.DamageCause.CONTACT,
                EntityDamageEvent.DamageCause.CRAMMING,
                EntityDamageEvent.DamageCause.DROWNING
        );

        for(EntityDamageEvent.DamageCause cause : causes){
            if(event.getEntity().getWorld().getName().equalsIgnoreCase("pvp") && event.getCause() == cause){
                event.setCancelled(true);
            }
        }

    }
}
