package fr.novalya.survival_utils.listeners;

import fr.novalya.survival_utils.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerLoginEvent;

public class JoinListener extends CustomListener<PlayerLoginEvent> {
    @Override
    public void run(PlayerLoginEvent event) {

        Main.getInstance().getAdressMap().put(Bukkit.getPlayer(event.getPlayer().getName()), event.getHostname());

    }
}
