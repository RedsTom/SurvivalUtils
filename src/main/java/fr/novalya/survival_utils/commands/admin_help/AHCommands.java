package fr.novalya.survival_utils.commands.admin_help;

import com.google.common.collect.Lists;
import fr.novalya.survival_utils.Main;
import fr.novalya.survival_utils.commands.sut.*;
import fr.novalya.survival_utils.commands.sut.utils.ArgumentUsages;
import fr.novalya.survival_utils.commands.sut.utils.CustomCommand;
import fr.novalya.survival_utils.commands.sut.utils.FunctionArgumentUsage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum AHCommands {

    HELP(new ArgumentUsages(Arrays.asList("help", "h", "?"), new FunctionArgumentUsage("", true) {
        @Override
        public List<String> apply(JavaPlugin plugin, CommandSender sender) {
            return Lists.newArrayList();
        }
    }), new HelpSubCommand("Aide de la commande /a?")),
    ROLE(new ArgumentUsages(Arrays.asList("ROLE", "role"), new FunctionArgumentUsage("role name", false) {
        @Override
        public List<String> apply(JavaPlugin plugin, CommandSender sender) {
            return Arrays.asList(Main.getPerms().getGroups());
        }
    }), new RoleCommand("")),
;
    private ArgumentUsages usage;
    private CustomCommand executor;

    AHCommands(ArgumentUsages usage, CustomCommand executor) {
        this.usage = usage;
        this.executor = executor;
    }

    public static AHCommands getArgument(String arg) {
        for (AHCommands SutCommands : values()) {
            if (SutCommands.name().equalsIgnoreCase(arg)) return SutCommands;
            if (SutCommands.getUsage().getAliases().contains(arg.toLowerCase())) return SutCommands;
        }
        return null;
    }

    public ArgumentUsages getUsage() {
        return usage;
    }

    public CustomCommand getExecutor() {
        return executor;
    }

}
