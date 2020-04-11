package fr.novalya.survival_utils.listeners;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.ExpBottleEvent;

public class EnchantListener extends CustomListener<PrepareItemEnchantEvent> {
    @Override
    public void run(PrepareItemEnchantEvent event) {

        if(event.getItem().getEnchantments().containsKey(Enchantment.MENDING)){

            event.getItem().removeEnchantment(Enchantment.MENDING);

        }

    }
}
