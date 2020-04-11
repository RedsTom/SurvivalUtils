package fr.novalya.survival_utils.commands.sut;

import fr.novalya.survival_utils.Data;
import fr.novalya.survival_utils.commands.sut.utils.CustomCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class BcSubCommand extends CustomCommand {
    public BcSubCommand(String description) {
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

        setSender(sender);

        if(args.length == 0) {
            msg(Data.getValue("prefix") + "<red>Syntaxe : /sut bc <args>");
            return;
        }

        StringBuilder sb = new StringBuilder("<gold>");

        for(String s : args){
            sb.append(s).append(" ");
        }

        msg(Data.getValue("prefix") + " Votre message a bien été envoyé");

        for(Player p : Bukkit.getOnlinePlayers())
        {
            setSender(p);
            msg(Data.getValue("info_prefix") + sb.toString());
        }
    }

}
