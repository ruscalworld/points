package ru.ruscalworld.points.common.core;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface OfflinePlayer {
    @NotNull UUID getUUID();
    @NotNull String getName();
}
