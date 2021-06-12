package ru.ruscalworld.points.common.core;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public interface CommandExecutor {
    @NotNull String getName();
    boolean hasPermission(String permission);
    boolean isPlayer();
    void sendMessage(Component component);
}
