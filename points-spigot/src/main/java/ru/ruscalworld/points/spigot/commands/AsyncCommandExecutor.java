package ru.ruscalworld.points.spigot.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.ruscalworld.points.common.core.CommandExecutor;
import ru.ruscalworld.points.spigot.impl.BukkitCommandExecutor;
import ru.ruscalworld.points.spigot.impl.BukkitPlayer;

import java.util.concurrent.CompletableFuture;

public abstract class AsyncCommandExecutor implements org.bukkit.command.CommandExecutor {
    @Override
    public final boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        CommandExecutor executor = sender instanceof Player ? new BukkitPlayer(sender) : new BukkitCommandExecutor(sender);
        CompletableFuture.runAsync(() -> this.onCommandAsync(executor, sender, command, label, args));
        return true;
    }

    public abstract void onCommandAsync(@NotNull CommandExecutor executor, @NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args);
}
