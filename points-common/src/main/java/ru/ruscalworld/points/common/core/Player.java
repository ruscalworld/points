package ru.ruscalworld.points.common.core;

import org.jetbrains.annotations.NotNull;
import ru.ruscalworld.points.common.util.Location;

import java.util.UUID;

public interface Player extends OfflinePlayer {
    @NotNull Location getLocation();
}
