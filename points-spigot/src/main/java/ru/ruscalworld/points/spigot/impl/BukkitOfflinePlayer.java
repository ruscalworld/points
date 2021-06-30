package ru.ruscalworld.points.spigot.impl;

import org.jetbrains.annotations.NotNull;
import ru.ruscalworld.points.common.core.OfflinePlayer;

import java.util.UUID;

public class BukkitOfflinePlayer implements OfflinePlayer {
    private final org.bukkit.OfflinePlayer bukkitPlayer;

    public BukkitOfflinePlayer(org.bukkit.OfflinePlayer player) {
        this.bukkitPlayer = player;
    }

    @Override
    public @NotNull UUID getUUID() {
        return this.getBukkitPlayer().getUniqueId();
    }

    @Override
    public @NotNull String getName() {
        return this.getBukkitPlayer().getName() != null ? this.getBukkitPlayer().getName() : "Unknown";
    }

    public org.bukkit.OfflinePlayer getBukkitPlayer() {
        return this.bukkitPlayer;
    }
}
