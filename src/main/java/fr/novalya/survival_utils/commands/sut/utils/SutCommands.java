package fr.novalya.survival_utils.commands.sut.utils;

import com.google.common.collect.Lists;
import fr.novalya.survival_utils.commands.sut.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum SutCommands {

    HELP(new ArgumentUsages(Arrays.asList("help", "h", "?"), new FunctionArgumentUsage("", true) {
        @Override
        public List<String> apply(JavaPlugin plugin, CommandSender sender) {
            return Lists.newArrayList();
        }
    }), new HelpSubCommand("Aide de la commande /sut")),

    RELOAD(new ArgumentUsages(Arrays.asList("reload", "rl"), new FunctionArgumentUsage("", true) {
        @Override
        public List<String> apply(JavaPlugin plugin, CommandSender sender) {
            return Lists.newArrayList();
        }
    }), new ReloadSubCommand("Recharge la configuration du plugin")),
    BROADCAST(new ArgumentUsages(Arrays.asList("bc", "broadcast"), new FunctionArgumentUsage("message", false) {
        @Override
        public List<String> apply(JavaPlugin plugin, CommandSender sender) {
            return Lists.newArrayList();
        }
    }), new BcSubCommand("Envoie un message a tout le serveur")),
    COLORS(new ArgumentUsages(Arrays.asList("colors", "color", "cl"), new FunctionArgumentUsage("", true) {
        @Override
        public List<String> apply(JavaPlugin plugin, CommandSender sender) {
            return Lists.newArrayList();
        }
    }), new ColorsSubCommand("Montre les couleurs du plugin")),
    ADD_INSULTE(new ArgumentUsages(Arrays.asList("add_badword", "addbd"), new FunctionArgumentUsage("insulte", false) {
        @Override
        public List<String> apply(JavaPlugin plugin, CommandSender sender) {
            return Lists.newArrayList();
        }
    }, new FunctionArgumentUsage("warn", false) {
        @Override
        public List<String> apply(JavaPlugin plugin, CommandSender sender) {
            return Lists.newArrayList("false", "true");
        }
    }), new AddInsulteSubCommand("Ajoute une insulte à la blacklist")),
    GET_IP(new ArgumentUsages(Arrays.asList("get_ip", "getip", "gip"), new FunctionArgumentUsage("player_name", false) {
        @Override
        public List<String> apply(JavaPlugin plugin, CommandSender sender) {
            List<String> toReturn = new ArrayList<>();
            for(Player p : Bukkit.getOnlinePlayers()){
                toReturn.add(p.getName());
            }
            return toReturn;
        }
    }), new GetIpSubCommand("Get the IP of the player"));
;
    private ArgumentUsages usage;
    private CustomCommand executor;

    SutCommands(ArgumentUsages usage, CustomCommand executor) {
        this.usage = usage;
        this.executor = executor;
    }

    public static SutCommands getArgument(String arg) {
        for (SutCommands SutCommands : values()) {
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
