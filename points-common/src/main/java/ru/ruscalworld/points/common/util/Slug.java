package ru.ruscalworld.points.common.util;

import java.util.HashMap;
import java.util.Locale;

public class Slug {
    private final static HashMap<String, String> TRANSLITERATION_MAP = new HashMap<String, String>() {{
        put("а", "a");
        put("б", "b");
        put("в", "v");
        put("г", "g");
        put("д", "d");
        put("е", "e");
        put("ё", "yo");
        put("ж", "zh");
        put("з", "z");
        put("и", "i");
        put("й", "j");
        put("к", "k");
        put("л", "l");
        put("м", "m");
        put("н", "n");
        put("о", "o");
        put("п", "p");
        put("р", "r");
        put("с", "s");
        put("т", "t");
        put("у", "u");
        put("ф", "f");
        put("х", "x");
        put("ц", "c");
        put("ч", "ch");
        put("ш", "shh");
        put("щ", "y");
        put("ь", "");
        put("э", "e");
        put("ю", "yu");
        put("я", "ya");
    }};

    public static String make(String input) {
        input = input.replace(" ", "-");
        input = input.toLowerCase(Locale.ROOT);
        input = transliterate(input);
        input = normalize(input);
        return input;
    }

    public static String normalize(String input) {
        return input.replaceAll("[^a-z0-9-]+", "");
    }

    public static String transliterate(String input) {
        for (String c : TRANSLITERATION_MAP.keySet()) {
            System.out.println(c + " " + input);
            input = input.replace(c, TRANSLITERATION_MAP.get(c));
        }

        return input;
    }
}
