package fr.novalya.survival_utils.commands;

import fr.novalya.survival_utils.Data;
import fr.novalya.survival_utils.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class
FreezeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if(args[0].isEmpty() || Bukkit.getPlayerExact(args[0]) == null){

            sender.sendMessage(Data.getValue("mod_prefix") + " §cSyntaxe incorrecte : /freeze [player]");
            return false;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if(target.hasPermission("sutils.cannot_freeze")){

            sender.sendMessage(Data.getValue("mod_prefix") + " §cLe joueur §6" + target.getDisplayName() + " §cne peut pas être freeze");
            return false;

        }

        if(label.equalsIgnoreCase("freeze")) {
            if(Main.getInstance().getFreezed().contains(target.getUniqueId())){
                sender.sendMessage(Data.getValue("mod_prefix") + " §cLe joueur §6" + args[0] + "§c est déjà §bfreeze");
                return false;
            }
            Main.getInstance().addFreezed(target);
            sender.sendMessage(Data.getValue("mod_prefix") + " §aLe joueur §6" + args[0] + " §aa bien été §bfreeze");
        }else{
            if(!Main.getInstance().getFreezed().contains(target.getUniqueId())){
                sender.sendMessage(Data.getValue("mod_prefix") + " §cLe joueur §6" + args[0] + "§c n'est pas §bfreeze");
                return false;
            }
            Main.getInstance().removeFreezed(target);
            sender.sendMessage(Data.getValue("mod_prefix") + " §aLe joueur §6" + args[0] + " §aa bien été §6unfreeze");
        }
        return false;
    }
}
