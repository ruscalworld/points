package ru.ruscalworld.points.common.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;

public class Components {
    public static Component button(Component component, Component hint, String command) {
        NamedTextColor color = NamedTextColor.DARK_GRAY;
        return Component.text("[", color)
                .append(component.color(color))
                .append(Component.text("]", color))
                .hoverEvent(HoverEvent.showText(hint))
                .clickEvent(ClickEvent.runCommand(command));
    }
}
