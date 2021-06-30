package ru.ruscalworld.points.common.core;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface PlayerManager {
    @Nullable OfflinePlayer getOfflinePlayer(UUID uuid);
}
