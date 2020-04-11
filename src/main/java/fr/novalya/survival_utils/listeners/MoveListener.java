package fr.novalya.survival_utils.listeners;

import fr.novalya.survival_utils.Data;
import fr.novalya.survival_utils.Main;
import org.bukkit.event.player.PlayerMoveEvent;

import java.time.LocalDateTime;
import java.util.Date;

public class MoveListener extends CustomListener<PlayerMoveEvent> {
    @Override
    public void run(PlayerMoveEvent event) {

        if(Main.getInstance().getFreezed().contains(event.getPlayer().getUniqueId())){
            event.setCancelled(true);
            event.getPlayer().sendTitle(Data.getValue("mod_prefix"),  "§cVous êtes immobilisé, vous ne pouvez pas bouger !", 0, 10, 0);        }

        LocalDateTime now = LocalDateTime.now();
        //LocalDateTime.parse("2020-1-1 00:02");
        if(now.getDayOfMonth() == 1 && now.getMonthValue() == 1 && now.getHour() == 0 && (now.getMinute() <= 15)){

            event.setCancelled(true);
            event.getPlayer().sendTitle(Data.getValue("mod_prefix"),  "§cVa passer un peu de bon temps avec ta famille", 0, 10, 0);

        }

    }
}
