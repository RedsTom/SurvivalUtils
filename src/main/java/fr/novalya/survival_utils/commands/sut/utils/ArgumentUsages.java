package fr.novalya.survival_utils.commands.sut.utils;

import java.util.List;

public class ArgumentUsages {

    private ArgumentUsage[] argumentUsages;
    private List<String> aliases;

    public ArgumentUsages(List<String> aliases, ArgumentUsage... argumentUsages) {
        this.argumentUsages = argumentUsages;
        this.aliases = aliases;
    }

    public ArgumentUsage[] getArgumentUsages() {
        return argumentUsages;
    }

    public ArgumentUsage getArgumentUsage(int i) {
        return argumentUsages[i];
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getUsage() {
        StringBuilder txt = new StringBuilder();
        for (ArgumentUsage arg : argumentUsages) {
            txt.append(arg.getUsage()).append(" ");
        }
        return txt.toString();
    }
}
