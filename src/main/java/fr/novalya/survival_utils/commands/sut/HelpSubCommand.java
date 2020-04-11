package fr.novalya.survival_utils.commands.sut;

import fr.novalya.survival_utils.Data;
import fr.novalya.survival_utils.commands.sut.utils.ArgumentUsage;
import fr.novalya.survival_utils.commands.sut.utils.CustomCommand;
import fr.novalya.survival_utils.commands.sut.utils.SutCommands;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class HelpSubCommand extends CustomCommand {

    public HelpSubCommand(String description) {
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

        msg(Data.START_EMBED("AIDE"));

        for (SutCommands d : SutCommands.values()) {

            StringBuilder usageList = new StringBuilder();

            for (ArgumentUsage u : d.getUsage().getArgumentUsages()) {
                usageList.append(u.getUsage().equals("") ? "" : " ").append(u.getUsage());
            }

            msg("<yellow>/sut <green>" + d.name().toLowerCase() + "<pink>" + usageList.toString() + "<yellow> : <blue>" + d.getExecutor().getDescription());
        }
        msg(Data.END_EMBED);
    }

}
