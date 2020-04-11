package fr.novalya.survival_utils.listeners;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.PrepareAnvilEvent;

public class CombineListener extends CustomListener<PrepareAnvilEvent> {
    @Override
    public void run(PrepareAnvilEvent event) {

        if(event.getResult().getEnchantments().containsKey(Enchantment.MENDING)){

            event.getInventory().setRepairCost(100);

        }

    }
}
