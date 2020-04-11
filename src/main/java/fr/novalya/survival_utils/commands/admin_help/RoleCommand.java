package fr.novalya.survival_utils.commands.admin_help;

import fr.novalya.survival_utils.Main;
import fr.novalya.survival_utils.commands.sut.utils.CustomCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class RoleCommand extends CustomCommand {
    public RoleCommand(String description) {
        super(description);
    }

    @Override
    public void runAsConsole(ConsoleCommandSender console, String[] args) {

    }

    @Override
    public void runAsPlayer(Player p, String[] args) {

        List<String> groups = Arrays.asList(Main.getPerms().getGroups());

        if(args.length < 1){
            sendError("Veuillez mentionner un role");
            return;
        }
        if(!(groups.contains(args[0]))){
            sendError("Le role mentionné n'est pas correct");
            return;
        }

        switch (args[0]){

            case "Membre":
            case "Gold":
            case "Gold+":
            case "default":
                sendError("Le grade ne fait pas parti du staff !");
                return;
            default:
                Main.getRoledCommands().sendAll(args[0], this, (args.length < 2 ||Integer.parseInt(args[1]) == 0 ? 1 : Integer.parseInt(args[1])));

        }


    }

    @Override
    public void run(CommandSender sender, String[] args) {

    }
}
