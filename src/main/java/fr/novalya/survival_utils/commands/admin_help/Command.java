package fr.novalya.survival_utils.commands.admin_help;

import java.util.Arrays;
import java.util.List;

public class Command {

    private String name;
    private String description;
    private List<String> roles;

    public Command(String name, String description, String... roles){
        this.name = name;
        this.description = description;
        this.roles = Arrays.asList(roles);
    }

    public String getName() {
        return name;
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getDescription() {
        return description;
    }
}
