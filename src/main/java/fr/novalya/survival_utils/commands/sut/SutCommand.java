package fr.novalya.survival_utils.commands.sut;

import com.google.common.collect.Lists;
import fr.novalya.survival_utils.commands.sut.utils.SutCommands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class SutCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String label, String[] args) {
        String arg = "help";
        if (args.length > 0) {
            arg = args[0];

            SutCommands commandList = SutCommands.getArgument(arg);
            if (commandList != null) {
                List<String> finalArgs = Lists.newArrayList(args);
                finalArgs.remove(0);
                commandList.getExecutor().onCommand(sender, arg1, args[0], finalArgs.toArray(new String[]{}));
            }
        } else {
            SutCommands commandList = SutCommands.getArgument(arg);
            if (commandList != null) {
                commandList.getExecutor().onCommand(sender, arg1, arg, args);
            }
        }

        return false;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command arg1, String arg2, String[] args) {

        if (args.length == 0) return null;

        SutCommands commandList = SutCommands.getArgument(args[0]);
        if (commandList == null || args.length <= 1) {
            List<String> tab = new ArrayList<>();
            for (SutCommands m : SutCommands.values()) {
                if (m.name().toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
                    tab.add(m.name().toLowerCase());
                } else {
                    for (String alias : m.getUsage().getAliases()) {
                        if (alias.startsWith(args[args.length - 1].toLowerCase())) tab.add(alias);
                    }
                }

            }
            return tab;
        }
        if (args.length - 1 <= commandList.getUsage().getArgumentUsages().length) {
            int argLengthMenusOne = args.length - 1;
            return commandList.getUsage().getArgumentUsage(args.length - 2).getTabArguments(args[argLengthMenusOne], sender);
        }

        return null;
    }
}
