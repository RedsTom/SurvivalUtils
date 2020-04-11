/*
 * Made by RedsTom
 * Discord > @RedsTom#0580
 * Email > tom.frefre.13@gmail.com
 */

package fr.novalya.survival_utils.maintenance.listeners;

import fr.novalya.survival_utils.listeners.CustomListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.server.ServerListPingEvent;

public class PingListener extends CustomListener<ServerListPingEvent> {
    @Override
    public void run(ServerListPingEvent event) {
        if(Bukkit.hasWhitelist())
            event.setMotd(ChatColor.translateAlternateColorCodes('&', "&8•        &6Halusion&f&o.fr    &8•\n" +
                    "        &cMaintenance en cours..."));
    }
}
