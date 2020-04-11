package fr.novalya.survival_utils;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.novalya.survival_utils.commands.*;
import fr.novalya.survival_utils.commands.admin_help.AHCommand;
import fr.novalya.survival_utils.commands.admin_help.CommandRegistry;
import fr.novalya.survival_utils.commands.sut.SutCommand;
import fr.novalya.survival_utils.commands.sut.utils.CustomCommand;
import fr.novalya.survival_utils.commands.sut.utils.Placeholders;
import fr.novalya.survival_utils.listeners.*;
import fr.novalya.survival_utils.maintenance.commands.CommandMaintenance;
import fr.novalya.survival_utils.maintenance.listeners.LoginListener;
import fr.novalya.survival_utils.maintenance.listeners.PingListener;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public final class Main extends JavaPlugin {

    final String[] subs = {
            "Connect", "ConnectOther", "IP", "PlayerCount", "GetServers", "Message", "Forward", "ForwardToPlayer", "UUID", "UUIDOther", "ServerIP", "KickPlayer"
    };

    private List<UUID> freezed;

    private Map<Player, String> lastMessages;

    public static List<Player> VOTANTS = Lists.newArrayList();
    public static CommandSender SONDAGE_SENDER;
    private static Main instance;

    private static Economy econ = null;
    private static Permission perms = null;

    private static CommandRegistry roledCommands;

    Map<Player, String> adressMap = null;
    Map<String, Boolean> insultes = null;
    String insulteReplace = "";

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {


        instance = this;

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessageListeners());
/*
        for(Player p : Bukkit.getOnlinePlayers()){
            sendPluginMessage(subs[5], p, new String[]{Data.getValue("prefix") + " §aLe serveur §csurvie§a vient de s'ouvrir !"});
        }*/

        registerCommands();
        registerDatas();
        registerEvents();

        saveDefaultConfig();

        Placeholders.init();
        insultes = Maps.newHashMap();

        lastMessages = Maps.newHashMap();

        roledCommands = new CommandRegistry();

        adressMap = Maps.newHashMap();

        freezed = Lists.newArrayList();

        registerInsultes();

        // TIMER FOR MESSAGES

        Bukkit.getScheduler().runTaskTimer(this, () -> {

            List<String> words = getConfig().getStringList("messages");

            for (Player p : Bukkit.getOnlinePlayers()){

                // RANDOM FOR CHOOSE THE MESSAGE TO SEND

                int randomNum = ThreadLocalRandom.current().nextInt(0, words.size());

                p.sendMessage(Data.getValue("info_prefix") + words.get(randomNum).replace("&", "§"));

            }

        }, 0, getConfig().getInt("delay") * 20);        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
        }
        setupPermissions();

        Bukkit.broadcastMessage(Data.getValue("info_prefix") + " §aPlugin de survie rechargé, le lag s'estompe !");
    }

    public static CommandRegistry getRoledCommands() {
        return roledCommands;
    }

    public void reloadAllConfig() {
        super.reloadConfig();
        registerInsultes();
    }

    private void registerInsultes() {

        ConfigurationSection insulteSection = getConfig().getConfigurationSection("insultes");
        if(insulteSection == null) {
            getLogger().warning("Les insultes sont vides, veuillez les remplir");
        }else{
            for(String insulte : insulteSection.getValues(false).keySet()){
                if(insulte.isEmpty()) continue;

                String thisPath = insulteSection.getCurrentPath() + "." + insulte;

                insultes.put(getConfig().getString(thisPath + ".name"), getConfig().getBoolean(thisPath + ".warn"));
            }
        }

        insulteReplace = getConfig().getString("replacement");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }
    private void setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp != null ? rsp.getProvider() : null;
    }

    @Override
    public void onDisable() {
        for(Player p : Bukkit.getOnlinePlayers()){
            if(p.getWorld().getName().equalsIgnoreCase("pvp")) {
                p.chat("/spawn");
                p.sendMessage(Data.getValue("prefix") + " §aLe plugin du Pvp se reload, vous avez été téléporté au spawn !");
            }
        }/*
        for(Player p : Bukkit.getOnlinePlayers()){
            sendPluginMessage(subs[0], p, new String[]{"Lobby"});
            p.sendMessage(Data.getValue("prefix") + "Le serveur a été fermé, vous avez donc été déplacé sur le lobby");
        }*/


        Bukkit.broadcastMessage(Data.getValue("info_prefix") + " §cPlugin de survie en reload, le serveur va laguer un court instant !");
    }

    public static Economy getEcon() {
        return econ;
    }
    public static Permission getPerms() {
        return perms;
    }

    public void registerEvents() {

        registerEvent(PlayerMoveEvent.class, new MoveListener());
        registerEvent(AsyncPlayerChatEvent.class, new ChatListener(), EventPriority.LOWEST);
        registerEvent(PlayerCommandPreprocessEvent.class, new CommandListener());
        registerEvent(EntityDeathEvent.class, new DeathListener());
        registerEvent(EntityDamageEvent.class, new DamageListener());
        registerEvent(PlayerInteractEvent.class, new InteractListener());
        registerEvent(PlayerDropItemEvent.class, new DropListener());
        registerEvent(BlockPlaceEvent.class, new PlaceListener());
        registerEvent(PlayerCommandSendEvent.class, new Command2Listener());
        registerEvent(PlayerLoginEvent.class, new JoinListener());
        registerEvent(InventoryClickEvent.class, new InventoryListener());
        registerEvent(EntitySpawnEvent.class, new SpawnListener());
        registerEvent(PrepareItemEnchantEvent.class, new EnchantListener());
        registerEvent(PrepareAnvilEvent.class, new CombineListener());

        //registerEvent(McMMOPlayerLevelUpEvent.class, new LevelUpListener());


        registerEvent(PlayerLoginEvent.class, new LoginListener());
        registerEvent(ServerListPingEvent.class, new PingListener());
        //registerEvent(AfkStatusChangeEvent.class, new AfkEvent());

    }

    private <T extends Event> void registerEvent(Class<T> event, CustomListener<T> file, EventPriority priority) {
        Bukkit.getPluginManager().registerEvent(event, file, priority, file, this);
    }

    public void registerCommands() {

        registerCommand("sondage", new SondageCommand());
        registerCommand("maintenance", new CommandMaintenance(""));
        registerCommand("sut", new SutCommand());
        registerCommand("rename", new CustomCommand("Permet de renomer un item") {
            @Override
            public void runAsConsole(ConsoleCommandSender console, String[] args) {

            }

            @Override
            public void runAsPlayer(Player p, String[] args) {

                StringBuilder sb = new StringBuilder("&r");
                for (String arg : args) {
                    sb.append(arg).append(" ");
                }

                int lenght = sb.toString().replace("&r", "").length();

                String s = sb.toString();

                int money = lenght * 2;

                if(lenght == 0) {money=10;}

                if(getEcon().getBalance(p) < money){

                    sendError("Vous n'avez pas assez d'argent !");

                }else{

                    EconomyResponse response = econ.withdrawPlayer(p, money);

                    if(response.transactionSuccess()){

                        ItemStack current = p.getInventory().getItemInMainHand();
                        if(current.getType() == Material.AIR || current.getType() == null){
                            sendError("Vous n'avez pas d'item en main !");
                            return;
                        }

                        ItemMeta im = current.getItemMeta();
                        if(s.equals("&r")) im.setDisplayName("");
                        else im.setDisplayName(s.replace("&", "§"));

                        money *= current.getAmount();
                        current.setItemMeta(im);

                        p.getInventory().setItemInMainHand(current);

                        msg("&aLe rename a fonctionné, votre item s'appelle désormais &r" + p.getInventory().getItemInMainHand().getItemMeta().getDisplayName() + "&a et vous avez perdu &c" + econ.format(money));

                    }else{
                        sendError("Réessayez, il y a eu un problème !");
                    }

                }

            }

            @Override
            public void run(CommandSender sender, String[] args) {

            }
        });
        registerCommand("adminhelp", new AHCommand());
        registerCommand("discord", new CustomCommand("Show the discord of the server") {
            @Override
            public void runAsConsole(ConsoleCommandSender console, String[] args) {

            }

            @Override
            public void runAsPlayer(Player p, String[] args) {

            }

            @Override
            public void run(CommandSender sender, String[] args) {

                sender.sendMessage(Data.getValue("prefix") + "&bLien du discord : <red>https://discord.gg/hZnk36a");

            }
        });
        registerCommand("asconsole", new AsConsoleCommand("Envoie une commande à la place de la console"));
        registerCommand("pvp", (commandSender, command, s, strings) -> {

            /*if(!(commandSender instanceof Player)) return false;
            ((Player) commandSender).getPlayer().chat("/warp Pvp_Arena");
            ((Player) commandSender).getPlayer().getInventory().clear();
            setupInventoryWithKitPvp((Player) commandSender, true);
            ((Player) commandSender).getPlayer().getInventory().setItemInOffHand(new ItemStack(Material.SANDSTONE, 64));
            setupInventoryWithKitPvp(((Player) commandSender).getPlayer(), false);
            commandSender.sendMessage(Data.getValue("prefix") + " §aVous avez été équipé pour pouvoir PvP, bonne chance !");
            ((Player) commandSender).setCooldown(Material.GOLDEN_APPLE, 0);
            for(PotionEffect effect : ((Player) commandSender).getActivePotionEffects())((Player) commandSender).removePotionEffect(effect.getType());
*/
            commandSender.sendMessage(Data.getValue("mod_prefix") + "§c La commande est désactivée");
            return false;
        });
        registerCommand("uuid", ((sender, command, label, args) -> {

            if(args.length == 0){
                sender.sendMessage(Data.getValue("mod_prefix") + "§c Syntaxe: /uuid [joueur]");
            }
            else {
                Bukkit.getOfflinePlayer(args[0]);
                sender.sendMessage(Data.getValue("mod_prefix") + " §aL'UUID du joueur §6" + args[0] + " §aest §9" + Bukkit.getOfflinePlayer(args[0]).getUniqueId().toString());
            }
            return false;

        }));
        registerCommand("tableflip", ((sender, command, label, args) -> {

            if(!(sender instanceof Player))
                    return false;

            ((Player) sender).getPlayer().chat("(╯°□°）╯︵ ┻━┻");
            return false;
        }));


        registerCommand("freeze", new FreezeCommand());
        registerCommand("unfreeze", new FreezeCommand());
        registerCommand("cheque", new ChequeCommand());
    }


    public void registerDatas() {

        Data.addValue("info_prefix", "§bNovaInfo §f- §r");
        Data.addValue("prefix", "§aNovaSurvival §f-§r");
        Data.addValue("chat_format", "%player%§7: " + ChatColor.DARK_GRAY);
        Data.addValue("mod_prefix", "§4NovaBan §f-§r");
        Data.addValue("eco_prefix", "§9Economie §f-§r");
        Data.addValue("active maintenance", ChatColor.translateAlternateColorCodes('&', "\n&8• &eNovalya &7| &cMaintenance\n\n&6Novalya est actuellement en maintenance.\n&cRaison : %s\n\n&8» &fPour plus d'informations rendez-vous sur notre discord\n\n&bhttps://novalya.eu"));

        if(getConfig().getString("reason").equals(""))
            Data.addValue("reason maintenance", ChatColor.translateAlternateColorCodes('&', "&cPas de raison définie !"));
        else
            Data.addValue("reason maintenance", ChatColor.translateAlternateColorCodes('&', getConfig().getString("reason")));

        getConfig().set("reason", Data.getValue("reason maintenance"));
        saveConfig();

    }

    private void registerCommand(String name, CommandExecutor cmd){
        getCommand(name).setExecutor(cmd);
    }

    private <T extends Event> void registerEvent(Class<T> event, CustomListener<T> file){
        Bukkit.getPluginManager().registerEvent(event, file, EventPriority.LOWEST, file, this);
    }

    public void addFreezed(Player p){
        freezed.add(p.getUniqueId());
    }
    public void removeFreezed(Player p){
        freezed.remove(p.getUniqueId());
    }

    public List<UUID> getFreezed() {
        return freezed;
    }

    public Map<String, Boolean> getBadWordsMap() {
        registerInsultes();
        return insultes;
    }
    public Set<String> getBadWord() {
        registerInsultes();
        return insultes.keySet();
    }


    public String getBadWordReplace() {
        registerInsultes();
        return insulteReplace;
    }

    public Map<Player,String> getAdressMap() {

        return adressMap;

    }

    private void sendPluginMessage(String sub, Player player, String args[]){

        final ByteArrayDataOutput message = ByteStreams.newDataOutput();
        message.writeUTF(sub);

        for(String arg : args){
            message.writeUTF(arg);
        }

        while(player == null){
            player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        }

        player.sendPluginMessage(getInstance(),"BungeeCord" , message.toByteArray());


    }

    private boolean isSubChannel(String subChannel){
        for(String sub : subs){
            if(subChannel.equals(sub)) return true;
        }
        return false;
    }

    public static List<String> tabCompletionByMap(String[] args, HashMap<String, String> subs, int position) {
        if(args.length <= position && args[position - 1].isEmpty()){
            return new ArrayList<>(subs.keySet());
        }else if (args.length <= position){
            List<String> toReturn = new ArrayList<>();
            for(String st: subs.keySet()){
                if(st.startsWith(args[position - 1]))
                    toReturn.add(st);
            }
            return toReturn;
        }
        return null;
    }

    public void sendHelp(HashMap<String, String> subs, CustomCommand cmd){
        sendHelp(subs, cmd, false);
    }
    public void sendHelp(HashMap<String, String> subs, CustomCommand cmd, boolean wrong){
        StringBuilder bl = new StringBuilder();
        subs.keySet().forEach(s -> bl
                .append("&8» &4/" + cmd.getName() + " &c")
                .append(s)
                .append(" ")
                .append(subs.get(s).replace("(", "&5(").replace("[", "&d["))
                .append("\n"));

        if(wrong) cmd.msg("&cMauvaise Commande !");
        cmd.msg("&cLes commandes possibles sont : \n" + bl.toString());
    }

    public Map<Player, String> getLastMessages() {
        return lastMessages;
    }
}
