package fr.novalya.survival_utils.listeners;

import fr.novalya.survival_utils.Data;
import fr.novalya.survival_utils.Main;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class InteractListener extends CustomListener<PlayerInteractEvent> {

    @Override
    public void run(PlayerInteractEvent event) {

        Player p = event.getPlayer();

        if(event.getItem() == null) return;

        switch (event.getItem().getType()) {
            case GOLDEN_APPLE:
            switch (event.getAction()){
                case RIGHT_CLICK_BLOCK:
                case RIGHT_CLICK_AIR:


                    event.setCancelled(true);

                    if(p.hasPotionEffect(PotionEffectType.ABSORPTION)) return;

                    ItemStack it = event.getItem();
                    int amount = it.getAmount();
                    it.setAmount(Math.max(amount - 1, 0));
                    event.getPlayer().setCooldown(it.getType(), 3000);

                    p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 3000, 4, false, false));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 50, false, false));

                    break;
            }
            break;
            case PAPER:

                if(event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6chèque")){
                    EconomyResponse r = Main.getEcon().depositPlayer(event.getPlayer(), Double.parseDouble(event.getItem().getItemMeta().getLore().get(0).replace("§0", "")));
                    if(r.transactionSuccess()) {
                        event.getPlayer().sendMessage(String.format(Data.getValue("eco_prefix") + " §aVous avez recu §6%s", Main.getEcon().format(r.amount)));
                        event.getItem().setAmount(event.getItem().getAmount() - 1);
                    } else {
                        event.getPlayer().sendMessage(String.format(Data.getValue("eco_prefix") + "§cUne erreur est survenue, contactez RedsTom S.V.P : %s", r.errorMessage));
                    }
                }
        }

    }
}
