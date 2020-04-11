package fr.novalya.survival_utils.commands.sut.utils;

import fr.novalya.survival_utils.Data;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import javax.xml.ws.RequestWrapper;

public abstract class CustomCommand implements CommandExecutor {

    private String description;
    private CommandSender sender;
    private String name;

    public CustomCommand(String description){
        this.description = description;
    }

    public void setSender(CommandSender sender) {
        this.sender = sender;
    }

    public void msg(String message) {
        for (String placeholder : Placeholders.getPlaceholders()) {
            message = message.replace(placeholder, "" + Placeholders.getColorFor(placeholder));
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public String getDescription() {
        return description;
    }

    @RequestWrapper
    public void setDescription(String description) {
        this.description = description;
    }

    public abstract void runAsConsole(ConsoleCommandSender console, String[] args);
    public abstract void runAsPlayer(Player p , String[] args);
    public abstract void run(CommandSender sender, String[] args);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        setSender(sender);
        name = command.getName();
        if(sender instanceof Player) runAsPlayer((Player) sender, args);
        if(sender instanceof ConsoleCommandSender) runAsConsole((ConsoleCommandSender) sender, args);
        run(sender, args);
        return false;
    }

    public void sendError(String error){
        msg(Data.getValue("prefix") + "&c " + error.replace("&r", "&c"));
    }

    public String getName() {
        return name;
    }
}
