package ru.ruscalworld.points.common.util;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;

public class Styles {
    public static Style main() {
        return Style.style(NamedTextColor.WHITE);
    }

    public static Style contrast() {
        return Style.style(NamedTextColor.GRAY);
    }
}
