package fr.novalya.survival_utils.listeners;

import com.google.common.collect.Lists;
import fr.novalya.survival_utils.Data;
import fr.novalya.survival_utils.Main;
import me.leoko.advancedban.manager.TimeManager;
import me.leoko.advancedban.utils.Punishment;
import me.leoko.advancedban.utils.PunishmentType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;

public class ChatListener extends CustomListener<AsyncPlayerChatEvent> {

    @Override
    public void run(AsyncPlayerChatEvent e) {

        String[] words = e.getMessage().split(" ");
        String message = e.getMessage();

        boolean isBadwords = false;

        if (Main.getInstance().getLastMessages().get(e.getPlayer()) == null)
            Main.getInstance().getLastMessages().put(e.getPlayer(), "");

        if (Main.getInstance().getLastMessages().get(e.getPlayer()).equalsIgnoreCase(e.getMessage())) {

            e.getPlayer().sendMessage(Data.getValue("prefix") + "§7 Vous ne pouvez pas envoyer deux fois le meme message !");
            e.setCancelled(true);
            return;
        }

        Main.getInstance().getLastMessages().put(e.getPlayer(), e.getMessage());

        for (String word : words) {
            for (String insulte : Main.getInstance().getBadWord()) {
                if (word.equalsIgnoreCase(insulte)/* && !e.getPlayer().hasPermission("sutils.bypass_chat")*/) {
                    e.setCancelled(true);
                    isBadwords = true;
                    if (Main.getInstance().getBadWordsMap().get(insulte))
                        new Punishment(e.getPlayer().getName(), e.getPlayer().getUniqueId().toString(), "Insultes", "§aAutomatic Punish", PunishmentType.TEMP_WARNING, TimeManager.getTime(), TimeManager.getTime() + 300000, null, -1).create();
                    message = message.replace(word, Main.getInstance().getBadWordReplace());
                }
            }
        }

        final boolean finalIsBadwords = isBadwords;

        Bukkit.getOnlinePlayers().forEach((p) -> {

            if (p.hasPermission("chat.view_badwords") && finalIsBadwords) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Original Message from &c" + e.getPlayer().getDisplayName() + " : \n&b" + e.getMessage()));
                e.getRecipients().remove(p);
            }
        });

        e.setMessage(message);
        e.setCancelled(false);


        if (Main.getInstance().getFreezed().contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(Data.getValue("mod_prefix") + "§cVous ne pouvez pas envoyer votre message car vous êtes freeze, les modérateurs verront quand même votre message : \n§6" + e.getMessage());

            for (Player p : Bukkit.getOnlinePlayers())
                if (p.hasPermission("sutils.freeze"))
                    p.sendMessage("§7Original Message from §c" + e.getPlayer().getName() + " §7: \n§3" + e.getMessage());
        }
/*
        //SONDAGE EN COURS ?
        if (e.getMessage().startsWith("%") && Main.VOTANTS.contains(e.getPlayer())) {
            e.setCancelled(true);

            e.getPlayer().sendMessage(Data.getValue("prefix") + " §aMerci d'avoir répondu au sondage");
            e.getPlayer().sendTitle("", "", 0, 1, 0);
            Main.SONDAGE_SENDER.sendMessage(Data.getValue("prefix") + " " + e.getPlayer().getDisplayName() + "§a à répondu à votre sondage : \n§6" + e.getMessage().replaceFirst("%", ""));
            for(Player p : Bukkit.getOnlinePlayers()){
                if(p.hasPermission("sutils.view") && p != Main.SONDAGE_SENDER) p.sendMessage(Data.getValue("prefix") + " " + e.getPlayer().getDisplayName() + "§a à répondu à votre sondage : \n§6" + e.getMessage().replaceFirst("%", ""));
            }
            Main.VOTANTS.remove(e.getPlayer());
            return;
        }
        */
/*
        e.setCancelled(true);
        String formatedMessage = "§7[§d" + e.getPlayer().getWorld().getName().replace("world", "Survie") + "§7] §r%s§a > §e%s";
        Bukkit.getOnlinePlayers().forEach((player) -> {

            if(formatedMessage.contains(player.getName()) || formatedMessage.contains(player.getDisplayName())) player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 10);

        });
        e.setFormat(String.format(ChatColor.translateAlternateColorCodes('§', formatedMessage), e.getPlayer().getDisplayName(), e.getMessage().replace("&r", "&e").replace("§r", "§e").replace("&", e.getPlayer().hasPermission("chat.color") ? "§" : "&")));
        e.setCancelled(false);
*/
        ArrayList<Character> majs = Lists.newArrayList(
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        );
        float lenght = 0;
        float caps = 0;

        StringBuilder letters = new StringBuilder();

        for (Character c : e.getMessage().toCharArray()) {

            if (c.equals(' ')) continue;

            lenght++;
            if (majs.contains(c)) caps++;
            letters.append(c);

        }
        float percentage = caps / lenght;
        float max = 3.0f / 4.0f;

        if (percentage >= max && lenght >= 5) {

            new Punishment(e.getPlayer().getName(), e.getPlayer().getUniqueId().toString(), "\n => Caps : " + percentage * 100 + "% / 75 %\n => Warn\n => Durée : 5minutes", "§aAutomatic Punish", PunishmentType.TEMP_WARNING, TimeManager.getTime(), TimeManager.getTime() + 300000, null, -1).create();

        }

    }
}
