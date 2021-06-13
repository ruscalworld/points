package ru.ruscalworld.points.common.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;

public class Messages {
    public static Component pointNotFound(String name) {
        return Component.translatable(
                "errors.point.notfound", Styles.main(),
                Component.text(name, Styles.contrast())
        );
    }

    public static Component unableToRetrieve() {
        return Component.translatable("errors.point.unknown", Styles.main());
    }

    public static Component unableToUpdate() {
        return Component.translatable("errors.point.update", Styles.main());
    }

    public static Component incorrectUsage(String name, String args) {
        return Component.translatable(
                "commands.usage", Styles.main(),
                Component.text("/", Styles.contrast())
                        .append(Component.text(name))
                        .append(Component.text(" "))
                        .append(Component.text(args))
                        .clickEvent(ClickEvent.suggestCommand("/" + name + " "))
                        .hoverEvent(HoverEvent.showText(Component.translatable(
                                "commands.click-to-suggest", Styles.main()
                        )))
        );
    }
}
