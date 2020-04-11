package fr.novalya.survival_utils.commands.admin_help;

import com.google.common.collect.Lists;
import fr.novalya.survival_utils.Data;
import fr.novalya.survival_utils.Main;
import fr.novalya.survival_utils.commands.sut.utils.CustomCommand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CommandRegistry {


    private List<Command> commands;

    public CommandRegistry(){

        commands = Lists.newArrayList();
        init();

    }

    private void init(){

        ConfigurationSection roleSection = Main.getInstance().getConfig().getConfigurationSection("commands");
        if(roleSection == null) {
            Main.getInstance().getLogger().warning("Les commandes sont vides, veuillez les remplir");
        }else{
            for(String commands : roleSection.getKeys(false)){
                if(commands.isEmpty()) continue;

                ConfigurationSection section = roleSection.getConfigurationSection(commands);
                assert section != null;
                String name = section.getString("name");
                String description = section.getString("description");
                List<String> roles = section.getStringList("roles");

                System.out.println(String.format("%s, %s, %s", name, description, roles.toString()));

                addCommand(new Command(name, description, roles.toArray(new String[]{})));
            }
        }

        /*addCommand("WorldEdit", "Éditer le monde", "Builder", "Modo", "Co-Admin", "Admin");
        addCommand("VoxelSniper", "Éditer le monde", "Builder", "Modo", "Co-Admin", "Admin");
        addCommand("/plugman reload §d<plugin name>", "Reload le plugin selectionné", "Developpeur", "Admin");
        addCommand("/plugman list", "Affiche les plugins du serveur","Admin", "Developpeur", "Modo");
        addCommand("/stop", "Redémarre le serveur", "Admin", "Developpeur");
        addCommand("/tempwarn §d<player> <duration> <reason>", "Averti le joueur séléctionné pour la periode de temps donnée", "Helper", "Modo", "Co-Admin", "Admin");
        addCommand("/warn §d<player> <reason>", "Averti le joueur séléctionné", "Modo", "Admin", "Co-Admin");*/
    }

    public void addCommand(Command c){
        commands.add(c);
    }
    public void addCommand(String name, String desc, String... roles){
        addCommand(new Command(name, desc, roles));
    }
    public void removeCommand(Command c){
        commands.remove(c);
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void sendAll(String role, CustomCommand cmd, int page) {

        if(!(commands.size() / 5 == 0))
        if(page > ((commands.size() / 5 ))) {cmd.sendError(String.format("La page renseignée est trop grande (§cPage: §9%s §c; Max: §9%s§c)", page, ((commands.size() / 5 ) == 0 ? 1 : commands.size() / 5))); return;}

        cmd.msg(Data.START_EMBED("Permissions : §a" + page + "/" + ((commands.size() / 5 ) == 0 ? 1 : commands.size() / 5)));

        List<Command> fives = Lists.newArrayList();

        int i = 1;
        int toPass = ((page * 5) - 5);
        for(Command commands : commands) {
            if(toPass != 0) {toPass --; continue;}
            if(i > 5 * page) continue;
            if(commands.getRoles().contains(role)) fives.add(commands);
            i++;
        }
        for(Command command : fives){
            cmd.msg("§6" + command.getName() + "§e: " + command.getDescription());
        }
        cmd.msg(Data.END_EMBED);

    }
}
