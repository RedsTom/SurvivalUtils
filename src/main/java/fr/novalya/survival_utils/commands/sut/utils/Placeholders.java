package fr.novalya.survival_utils.commands.sut.utils;

import com.google.common.collect.Maps;
import org.bukkit.ChatColor;

import javax.xml.ws.RequestWrapper;
import java.util.HashMap;
import java.util.Set;

public class Placeholders {


    private static HashMap<String, ChatColor> placeholders;

    private static ChatColor resetColor;

    public static void init() {
        placeholders = Maps.newHashMap();

        placeholders.put("<blue>", ChatColor.BLUE);
        placeholders.put("<red>", ChatColor.RED);
        placeholders.put("<green>", ChatColor.GREEN);
        placeholders.put("<cyan>", ChatColor.AQUA);
        placeholders.put("<gray>", ChatColor.GRAY);
        placeholders.put("<yellow>", ChatColor.YELLOW);
        placeholders.put("<white", ChatColor.WHITE);
        placeholders.put("<pink>", ChatColor.LIGHT_PURPLE);
        placeholders.put("<purple>", ChatColor.DARK_PURPLE);
        placeholders.put("<dark_green>", ChatColor.GREEN);
        placeholders.put("<dark_blue>", ChatColor.DARK_BLUE);
        placeholders.put("<dark_red>", ChatColor.DARK_RED);
        placeholders.put("<aqua>", ChatColor.DARK_AQUA);
        placeholders.put("<black>", ChatColor.BLACK);
        placeholders.put("<gold>", ChatColor.GOLD);
        placeholders.put("<dark_gray>", ChatColor.DARK_GRAY);

        placeholders.put("<magic>", ChatColor.MAGIC);
        placeholders.put("<b>", ChatColor.BOLD);
        placeholders.put("<st>", ChatColor.STRIKETHROUGH);
        placeholders.put("<u>", ChatColor.UNDERLINE);
        placeholders.put("<i>", ChatColor.ITALIC);

        placeholders.put("<r>", ChatColor.RESET);
    }

    public static ChatColor getColorFor(String key) {
        return placeholders.getOrDefault(key, placeholders.get("<r>"));
    }

    public static HashMap<String, ChatColor> getReplacements() {
        return placeholders;
    }

    public static Set<String> getPlaceholders() {
        return placeholders.keySet();
    }


    @RequestWrapper
    public static void setResetColor(ChatColor resetColor) {
        Placeholders.resetColor = resetColor;
    }
}
