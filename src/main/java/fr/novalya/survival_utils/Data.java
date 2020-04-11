package fr.novalya.survival_utils;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;

public class Data {

    private static HashMap<String, String> datas = new HashMap<>();

    public static void addValue(String key, String val){

        datas.put(key, val);

    }

    public static String getValue(String key){
        return datas.getOrDefault(key, "§c§lNONE");
    }

    public static String START_EMBED(String embed_name) {
        return "<gold>-------- <blue>" + embed_name + " <gold>--------";
    }

    public static final String END_EMBED = "<gold>---------------------------------";
}
