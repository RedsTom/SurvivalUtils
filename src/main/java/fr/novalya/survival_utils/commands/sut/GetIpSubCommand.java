package fr.novalya.survival_utils.commands.sut;

import fr.novalya.survival_utils.Data;
import fr.novalya.survival_utils.Main;
import fr.novalya.survival_utils.commands.sut.utils.CustomCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class GetIpSubCommand extends CustomCommand {
    public GetIpSubCommand(String description) {
        super(description);
    }

    @Override
    public void runAsConsole(ConsoleCommandSender console, String[] args) {

    }

    @Override
    public void runAsPlayer(Player p, String[] args) {

    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if(args.length == 0){
            sender.sendMessage(Data.getValue("mod_prefix") + "§c Syntaxe: /sut getip [joueur]");
        }
        else if(Bukkit.getOfflinePlayer(args[0]) == null){
            sender.sendMessage(Data.getValue("mod_prefix") + "§c Le joueur demandé n'existe pas");
        }
        else if(!Main.getInstance().getAdressMap().containsKey(Bukkit.getPlayerExact(args[0]))){
            sender.sendMessage(Data.getValue("mod_prefix") + "§c Le joueur indiqué n'est pas dans la base de données, il doit se déconnecter puis se reconnecter pour être dedans !");
        }
        else{
            sender.sendMessage(Data.getValue("mod_prefix") + " §aL'IP du joueur §6" + args[0] + " §aest §9" + Main.getInstance().getAdressMap().get(Bukkit.getPlayerExact(args[0])));
        }
    }

}
