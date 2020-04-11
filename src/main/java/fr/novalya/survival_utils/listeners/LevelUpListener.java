package fr.novalya.survival_utils.listeners;

public class LevelUpListener /*extends CustomListener<McMMOPlayerLevelUpEvent>*/ {
/*
    @Override
    public void run(McMMOPlayerLevelUpEvent event) {

        EconomyResponse response = Main.getEcon().depositPlayer(event.getPlayer(), (event.getSkillLevel() * event.getPlayer().getExpToLevel()) / 100);
        if(response.transactionSuccess()){

            event.getPlayer().sendMessage(Data.getValue("eco_prefix") + " §aVous avez reçu §6" + Main.getEcon().format(response.amount) + "§a pour être passé niveau §2" + event.getSkillLevel() + "§a en §b" + event.getSkill().getName());

        }else{
            event.getPlayer().sendMessage(String.format(Data.getValue("eco_prefix") + "§cUne erreur est survenue, contactez RedsTom S.V.P : §c%s", response.errorMessage));
        }


    }
*/
}
