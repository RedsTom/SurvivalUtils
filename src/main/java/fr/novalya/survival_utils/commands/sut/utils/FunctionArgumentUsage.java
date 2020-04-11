package fr.novalya.survival_utils.commands.sut.utils;

import fr.novalya.survival_utils.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public abstract class FunctionArgumentUsage implements ArgumentUsage {

    private String name;
    private boolean optional;

    public FunctionArgumentUsage(String name, boolean optional) {
        this.optional = optional;
        this.name = name;
    }

    @Override
    public String getUsage() {
        if (name.isEmpty()) return "";
        return optional ? "[" + name + "]" : "<" + name + ">";
    }


    @Override
    public boolean isOptional() {
        return optional;
    }

    @Override
    public List<String> getTabArguments(String tab, CommandSender sender) {
        List<String> completion = new ArrayList<>();
        for (String arg : apply(Main.getInstance(), sender)) {
            if (arg.toLowerCase().startsWith(tab.toLowerCase())) completion.add(arg);
        }
        return completion;
    }

    @Override
    public String[] getArguments() {
        return new String[]{name};
    }

    public abstract List<String> apply(JavaPlugin plugin, CommandSender sender);
}
