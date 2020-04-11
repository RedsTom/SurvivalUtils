package fr.novalya.survival_utils.commands.sut;

import fr.novalya.survival_utils.Data;
import fr.novalya.survival_utils.commands.sut.utils.CustomCommand;
import fr.novalya.survival_utils.commands.sut.utils.Placeholders;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ColorsSubCommand extends CustomCommand {
    public ColorsSubCommand(String description) {
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


        msg(Data.START_EMBED("COULEURS"));
        for(String s : Placeholders.getPlaceholders()){

            sender.sendMessage("§6" + s + " : " + Placeholders.getColorFor(s) + Placeholders.getColorFor(s).name());

        }
        msg(Data.END_EMBED);

    }
}
