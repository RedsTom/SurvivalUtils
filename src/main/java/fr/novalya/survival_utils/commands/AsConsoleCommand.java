package fr.novalya.survival_utils.commands;

import fr.novalya.survival_utils.Data;
import fr.novalya.survival_utils.Main;
import fr.novalya.survival_utils.commands.sut.utils.CustomCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class AsConsoleCommand extends CustomCommand {
    public AsConsoleCommand(String description) {
        super(description);
    }

    @Override
    public void runAsConsole(ConsoleCommandSender console, String[] args) {
        sendError("Vous devez être un joueur pour executer cette commande !");
    }

    @Override
    public void runAsPlayer(Player p, String[] args) {


        if(args.length == 0){
            sendError("Veuillez préciser la commande à executer !");
            return;
        }

        StringBuilder command = new StringBuilder();

        for (String arg : args) {
            if(arg.isEmpty()) continue;
            command.append(arg).append(" ");
        }

        ConsoleCommandSender sender1 = Main.getInstance().getServer().getConsoleSender();

        Bukkit.dispatchCommand(sender1, command.toString());

        msg(Data.getValue("mod_prefix") + " La commande a bien été envoyée !");

    }

    @Override
    public void run(CommandSender sender, String[] args) {

    }

}
