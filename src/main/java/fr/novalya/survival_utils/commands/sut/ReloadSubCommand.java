package fr.novalya.survival_utils.commands.sut;

import fr.novalya.survival_utils.Data;
import fr.novalya.survival_utils.Main;
import fr.novalya.survival_utils.commands.sut.utils.CustomCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ReloadSubCommand extends CustomCommand {
    public ReloadSubCommand(String description) {
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

        Main.getInstance().reloadAllConfig();

        msg(Data.getValue("prefix") + " <green>La configuration a bien été rechargée");
    }

}
