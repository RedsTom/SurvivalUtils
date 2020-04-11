package fr.novalya.survival_utils.listeners;

import fr.novalya.survival_utils.Data;
import fr.novalya.survival_utils.Main;
import fr.novalya.survival_utils.listeners.CustomListener;
import net.ess3.api.IUser;
import net.ess3.api.events.AfkStatusChangeEvent;
import org.bukkit.Bukkit;

public class AfkEvent extends CustomListener<AfkStatusChangeEvent> {
    @Override
    public void run(AfkStatusChangeEvent event) {

        IUser p = event.getAffected();

        Runnable r = () -> {
            if(p.isAfk() &&!p.getBase().hasPermission("sutils.bypass_antiafk")) p.getBase().kickPlayer(Data.getValue("mod_prefix") + "§b AFK plus de 10 minutes");
        };

        Bukkit.getScheduler().runTaskLater(Main.getInstance(), r, 12000);

    }
}
