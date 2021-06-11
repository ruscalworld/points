package ru.ruscalworld.points.common.core;

import org.jetbrains.annotations.NotNull;

public interface CommandExecutor {
    @NotNull String getName();
    boolean hasPermission(String permission);
}
