package fr.novalya.survival_utils.listeners;

import fr.novalya.survival_utils.Data;
import fr.novalya.survival_utils.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener extends CustomListener<PlayerCommandPreprocessEvent> {
    @Override
    public void run(PlayerCommandPreprocessEvent e) {

        if (Main.getInstance().getFreezed().contains(e.getPlayer()) && !e.getMessage().startsWith("/unfreeze")){
            e.setCancelled(true);
            e.getPlayer().sendMessage(Data.getValue("mod_prefix") + " §cSachant que vous êtes freeze, vous ne pouvez pas envoyer la commande §7" + e.getMessage());

            for(Player p : Bukkit.getOnlinePlayers()) if(p.hasPermission("sutils.freeze")) p.sendMessage(ChatColor.RED + e.getPlayer().getName() + "§7 a essayé d'éxecuter la commande §c" + e.getMessage());
            return;
        }

        if(e.getMessage().equalsIgnoreCase("/kit pvp") || e .getMessage() . equalsIgnoreCase("/kits pvp") || e.getMessage().equalsIgnoreCase("/essentials:kit pvp") || e .getMessage() . equalsIgnoreCase("/essentials:kits pvp")) {
            if (!e.getPlayer().getWorld().getName().equalsIgnoreCase("pvp")) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(Data.getValue("prefix") + "§c Désolé, mais vous devez être dans le warp §6Pvp Arena §cpour pouvoir utiliser ce kit");
            }
        }
    }
}
