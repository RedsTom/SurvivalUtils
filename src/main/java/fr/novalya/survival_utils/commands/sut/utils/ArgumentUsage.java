package fr.novalya.survival_utils.commands.sut.utils;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface ArgumentUsage {

    String getUsage();

    String[] getArguments();

    boolean isOptional();

    List<String> getTabArguments(String arg, CommandSender sender);
}
