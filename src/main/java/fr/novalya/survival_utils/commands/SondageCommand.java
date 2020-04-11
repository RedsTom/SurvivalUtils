package fr.novalya.survival_utils.commands;

import fr.novalya.survival_utils.Data;
import fr.novalya.survival_utils.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class SondageCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender player, Command command, String s1, String[] strings) {
        String prefix = "§4SONDAGE §c(§b%+réponse§c)";
        StringBuilder sb = new StringBuilder();

        for (String s : strings){

            sb.append(s).append(" ");

        }

        if(sb.toString().isEmpty() || sb.toString().equals(" ")){
            player.sendMessage(Data.getValue("prefix") + " §cVeuillez préciser une question");
            return false;
        }

        Main.SONDAGE_SENDER = player;


        for(Player p : Bukkit.getOnlinePlayers()){
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 15);
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 15);
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 15);
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 15);
            Main.VOTANTS.add(p);
            p.sendMessage(Data.getValue("prefix") + " §a" + player.getName() + " §6à commencé un sondage : \n§9" + sb.toString());

        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
