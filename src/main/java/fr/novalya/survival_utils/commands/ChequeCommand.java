package fr.novalya.survival_utils.commands;

import fr.novalya.survival_utils.Data;
import fr.novalya.survival_utils.ItemBuilder;
import fr.novalya.survival_utils.Main;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChequeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try{
            if(args.length == 0 || args[0].isEmpty()){
                sender.sendMessage(Data.getValue("eco_prefix") + " §cVeuillez préciser le montant du chèque");
                return false;
            }
            if(!(sender instanceof Player)){
                sender.sendMessage(Data.getValue("eco_prefix") + " §cVous devez être un joueur pour executer cette commande");
                return false;
            }
            Player p = (Player) sender;
            if(Double.parseDouble(args[0]) == 0){
                p.sendMessage(Data.getValue("eco_prefix") + " §cSyntaxe : /cheque [montant]");
                return false;
            }
            if(Main.getEcon().getBalance(p) - Double.parseDouble(args[0]) < 0){
                p.sendMessage(Data.getValue("eco_prefix") + " §cVous n'avez pas le solde necessaire à la transaction");
                return false;
            }
            if(p.getInventory().getContents().length == 27){
                p.sendMessage(Data.getValue("eco_prefix") +  " §cVotre inventaire est full, vous ne pouvez donc pas créer de chèque");
                return false;
            }

            //IF ALL IS FINE
            p.getInventory().addItem(new ItemBuilder(Material.PAPER, 1)
                    .setName("§6Chèque")
                    .addLoreLine("§0" + args[0])
                    .addLoreLine("§9Chèque de :")
                    .addLoreLine("§a   " + p.getName())
                    .addLoreLine("§9Valeur : ")
                    .addLoreLine("§a   " + Main.getEcon().format(Double.parseDouble(args[0])))
                    .toItemStack());
            EconomyResponse r = Main.getEcon().withdrawPlayer(p, Double.parseDouble(args[0]));
            if(r.transactionSuccess()) {
                p.sendMessage(String.format(Data.getValue("eco_prefix") + "§aVous avez recu un chèque d'une valeur de §6%s", Main.getEcon().format(r.amount)));
            } else {
                p.sendMessage(String.format(Data.getValue("eco_prefix") + "§cUne erreur est survenue, contactez RedsTom S.V.P : §c%s", r.errorMessage));
            }

        }catch (NumberFormatException ex){
            sender.sendMessage(Data.getValue("eco_prefix") + " §cSyntaxe : /cheque [montant]");
        }

        return false;
    }
}
