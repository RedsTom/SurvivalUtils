/*
 * Made by RedsTom
 * Discord > @RedsTom#0580
 * Email > tom.frefre.13@gmail.com
 */

package fr.novalya.survival_utils.maintenance.commands;

import com.google.common.collect.Maps;
import fr.novalya.survival_utils.Data;
import fr.novalya.survival_utils.Main;
import fr.novalya.survival_utils.commands.sut.utils.CustomCommand;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CommandMaintenance extends CustomCommand implements TabCompleter {

    HashMap<String, String> subs = Maps.newHashMap();

    public CommandMaintenance(String description) {
        super(description);
    }

    @Override
    public void runAsConsole(ConsoleCommandSender console, String[] args) {
        //WON'T BE USED
    }

    @Override
    public void runAsPlayer(Player p, String[] args) {
        //WON'T BE USED
    }

    @SuppressWarnings("deprecated")
    @Override
    public void run(CommandSender sender, String[] args) {

        subs.put("off", "");
        subs.put("on", "");
        subs.put("add", "[player]");
        subs.put("remove", "[player]");
        subs.put("list", "");
        subs.put("reason", "[reason]");

        if(args.length <= 0 || !(subs.containsKey(args[0]))){
            Main.getInstance().sendHelp(subs, this);
            return;
        }
        if(args[0].equalsIgnoreCase("off")){

            //OFF
            if(!Bukkit.hasWhitelist()){
                sendError("La maintenance est déjà inactive !");
                return;
            }
            Bukkit.setWhitelist(false);
            msg(Data.getValue("prefix") + "&fLa maintenance a bien été &cdesactivée");

        }else
        if(args[0].equalsIgnoreCase("on")){

            //ON
            if(Bukkit.hasWhitelist()){
                sendError("La maintenance est déjà active !");
                return;
            }
            Bukkit.setWhitelist(true);
            msg(Data.getValue("prefix") + "&fLa maintenance a bien été &aactivée");

        }else
        if (args[0].equalsIgnoreCase("add")){

            //ADD
            if(args.length <= 1){
                sendError("Veuillez mentionner un joueur !");
                return;
            }
            OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
            if(p == null){
                sendError("Le joueur mentionné n'existe pas ou ne s'est jamais connecté !");
                return;
            }
            if(Bukkit.getWhitelistedPlayers().contains(p)){
                sendError("Le joueur &7" + p.getName() + " &rest déjà dans la maintenance !");
                return;
            }
            p.setWhitelisted(true);
            msg(Data.getValue("prefix") + "&fLe joueur &7" + p.getName() + " &fa bien été &aajouté&f à la maintenance");
        }else
        if (args[0].equalsIgnoreCase("remove")){

            //REMOVE
            if(args.length <= 1){
                sendError("Veuillez mentionner un joueur !");
                return;
            }
            OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
            if(p == null){
                sendError("Le joueur mentionné n'existe pas ou ne s'est jamais connecté !");
                return;
            }
            if(!Bukkit.getWhitelistedPlayers().contains(p)){
                sendError("Le joueur &7" + p.getName() + " &rn'est pas dans la maintenance !");
                return;
            }
            p.setWhitelisted(false);
            msg(Data.getValue("prefix") + "&fLe joueur &7" + p.getName() + " &fa bien été &cretiré&f de la maintenance");
        }else if(args[0].equalsIgnoreCase("list")){

            //LIST
            StringBuilder sb = new StringBuilder();
            Bukkit.getWhitelistedPlayers().forEach((e) -> sb.append("\n§6").append(e.getName()));
            if(Bukkit.getWhitelistedPlayers().size() == 0){
                sendError("Il n'y a pas de joueurs dans la maintenance !");
                return;
            }
            msg(Data.getValue("prefix") + "&cLes joueurs dans la maintenance sont " + sb.toString());

        }else if(args[0].equalsIgnoreCase("reason") && args.length >= 2){

            StringBuilder sb = new StringBuilder();
            int lenght = 0;
            for(String arg : args){

                if(lenght < 1){ lenght++;}

                else sb.append(" ").append(ChatColor.translateAlternateColorCodes('&', arg));

            }
            Data.addValue("reason maintenance", ChatColor.translateAlternateColorCodes('&', "&c" + sb.toString()));

            Main.getInstance().getConfig().set("reason", Data.getValue("reason maintenance"));
            Main.getInstance().saveConfig();

            msg(Data.getValue("prefix") + "§aLa raison de la maintenance a bien été mise à jour vers : &r" + Data.getValue("reason maintenance"));
        }
        else {
            Main.getInstance().sendHelp(subs, this);
        }


    }


    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        return Main.tabCompletionByMap(args, this.subs, 1);
    }
}
