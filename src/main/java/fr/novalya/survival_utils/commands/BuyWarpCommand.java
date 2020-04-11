package fr.novalya.survival_utils.commands;

import com.earth2me.essentials.commands.EssentialsCommand;
import fr.novalya.survival_utils.Data;
import fr.novalya.survival_utils.Main;
import fr.novalya.survival_utils.commands.sut.utils.Placeholders;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuyWarpCommand extends EssentialsCommand implements CommandExecutor{

    private CommandSender sender;

    public void setSender(CommandSender sender) {
        this.sender = sender;
    }

    public void msg(String message) {
        for (String placeholder : Placeholders.getPlaceholders()) {
            message = message.replace(placeholder, "" + Placeholders.getColorFor(placeholder));
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public void sendError(String error){
        msg(Data.getValue("prefix") + "&c " + error.replace("&r", "&c"));
    }

    public BuyWarpCommand() {
        super("buywarp");
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player)
            return false;

        Player p = (Player) sender;
        setSender(p);

        double money = Main.getEcon().getBalance(p);

        if(money - 500 < 0){

            sendError("Vous ne pouvez pas acheter ce warp !");
            return false;
        }
        if(args.length != 1){
            sendError("Syntaxe : /bw [name]");
            return false;
        }

        //ALL IS FINE

        try {
            ess.getWarps().setWarp(args[0], p.getLocation());
            EconomyResponse response = Main.getEcon().withdrawPlayer(p, 500);
            if(!response.transactionSuccess()) {sendError("Il y a eu un problème lors du traitement de votre requête ! Si ce problème persiste, merci de contacter &6RedsTom &r!"); return false;}
            msg(Data.getValue("prefix") + "&aVotre warp &6" + args[0] + "&a a bien été créé ! Vous pouvez désormais vous y rendre en entrant la commande &c/warp " + args[0]);
        } catch (Exception e) {
            sendError("Il y a eu un problème lors du traitement de votre requête ! Si ce problème persiste, merci de contacter &6RedsTom &r!");
            e.printStackTrace();
        }

        return false;
    }
}
