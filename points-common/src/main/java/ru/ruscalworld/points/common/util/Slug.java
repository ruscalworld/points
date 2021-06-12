package ru.ruscalworld.points.common.util;

public class Slug {
    public static String make(String input) {
        input = input.replace(" ", "-");
        // TODO: Implement replacement of all unsupported characters
        return input;
    }

    public static String transliterate(String input) {
        // TODO: Implement transliteration
        return input;
    }
}
