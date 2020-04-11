/*
 * Made by RedsTom
 * Discord > @RedsTom#0580
 * Email > tom.frefre.13@gmail.com
 */

package fr.novalya.survival_utils.maintenance.listeners;

import fr.novalya.survival_utils.Data;
import fr.novalya.survival_utils.listeners.CustomListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Date;

public class LoginListener extends CustomListener<PlayerLoginEvent> {
    @Override
    public void run(PlayerLoginEvent e) {

        if(Bukkit.hasWhitelist()){
            Player player = e.getPlayer();
            if(!player.isWhitelisted()) {
                e.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, String.format(Data.getValue("active maintenance"), Data.getValue("reason maintenance")));
            }

        }

    }
}
