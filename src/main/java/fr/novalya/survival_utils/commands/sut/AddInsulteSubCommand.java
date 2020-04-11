package fr.novalya.survival_utils.commands.sut;

import fr.novalya.survival_utils.Data;
import fr.novalya.survival_utils.commands.sut.utils.CustomCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.io.File;

import static fr.novalya.survival_utils.Main.getInstance;

public class AddInsulteSubCommand extends CustomCommand {
    public AddInsulteSubCommand(String description) {
        super(description);
    }

    @Override
    public void runAsConsole(ConsoleCommandSender console, String[] args) {
        //ALL
    }

    @Override
    public void runAsPlayer(Player p, String[] args) {
        //ALL
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        setSender(sender);

        if(args.length < 2){
            msg(Data.getValue("prefix") + "<red>Syntaxe : /sut addbd <insulte> <warn>");
            return;
        }
        try{
            StringBuilder sb = new StringBuilder();
            sb.append(args[0]);

            //REGISTER IN CONFIG
            ConfigurationSection insulteSection = getInstance().getConfig().getConfigurationSection("insultes");

            String path = sb.toString().toLowerCase().replace(" ", "_");
            String name = sb.toString();
            boolean warn = Boolean.parseBoolean(args[1]);

            getInstance().getConfig().set(insulteSection.getCurrentPath() + "." + path + ".name", name);
            getInstance().getConfig().set(insulteSection.getCurrentPath() + "." + path + ".warn", warn);

            getInstance().getConfig().save(getInstance().getDataFolder() + File.separator + "config.yml");

            msg(Data.getValue("prefix") + "<green>L'insulte <gold>" + args[0] + "<green> a bien été ajoutée à la blacklist");
        } catch (Exception e) {
            msg(Data.getValue("prefix") + "<red>Syntaxe : /sut addbd <insulte> <warn>");
        }
    }

}
