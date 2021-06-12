package ru.ruscalworld.points.spigot.impl;

import org.jetbrains.annotations.NotNull;
import ru.ruscalworld.points.common.core.Player;
import ru.ruscalworld.points.common.util.Location;

import java.util.UUID;

public class BukkitPlayer extends BukkitCommandExecutor implements Player {
    public BukkitPlayer(org.bukkit.entity.Player player) {
        super(player);
    }

    @Override
    public @NotNull UUID getUUID() {
        return this.getBukkitPlayer().getUniqueId();
    }

    @Override
    public @NotNull Location getLocation() {
        org.bukkit.Location location = this.getBukkitPlayer().getLocation();
        String worldName = null;
        if (location.getWorld() != null) worldName = location.getWorld().getName();
        return new Location(location.getBlockX(), location.getBlockY(), location.getBlockZ(), worldName);
    }

    public org.bukkit.entity.Player getBukkitPlayer() {
        return ((org.bukkit.entity.Player) this.getBukkitExecutor());
    }
}
