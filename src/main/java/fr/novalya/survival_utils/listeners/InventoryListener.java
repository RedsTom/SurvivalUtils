package fr.novalya.survival_utils.listeners;

import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener extends CustomListener<InventoryClickEvent> {
    @Override
    public void run(InventoryClickEvent event) {

        if(event.getWhoClicked().getWorld().getName().equalsIgnoreCase("pvp") && !event.getWhoClicked().hasPermission("sutils.bypass_pvp")) event.setCancelled(true);

    }
}
