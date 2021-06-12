package ru.ruscalworld.points.spigot.impl;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.ruscalworld.points.spigot.PointsSpigot;
import ru.ruscalworld.points.common.core.CommandExecutor;

public class BukkitCommandExecutor implements CommandExecutor {
    private final CommandSender bukkitExecutor;

    public BukkitCommandExecutor(CommandSender executor) {
        this.bukkitExecutor = executor;
    }

    @Override
    public @NotNull String getName() {
        return this.getBukkitExecutor().getName();
    }

    @Override
    public boolean hasPermission(String permission) {
        return this.getBukkitExecutor().hasPermission(permission);
    }

    @Override
    public void sendMessage(Component component) {
        Audience audience = PointsSpigot.getInstance().getAdventure().sender(this.getBukkitExecutor());
        audience.sendMessage(component);
    }

    public CommandSender getBukkitExecutor() {
        return bukkitExecutor;
    }
}
