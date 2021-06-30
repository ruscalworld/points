package ru.ruscalworld.points.spigot.impl;

import org.bukkit.Bukkit;
import ru.ruscalworld.points.common.core.OfflinePlayer;
import ru.ruscalworld.points.common.core.PlayerManager;

import java.util.UUID;

public class BukkitPlayerManager implements PlayerManager {
    @Override
    public OfflinePlayer getOfflinePlayer(UUID uuid) {
        org.bukkit.OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        return new BukkitOfflinePlayer(player);
    }
}
