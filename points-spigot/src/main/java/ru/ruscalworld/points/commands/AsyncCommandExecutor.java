package ru.ruscalworld.points.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public abstract class AsyncCommandExecutor implements CommandExecutor {
    @Override
    public final boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        CompletableFuture.runAsync(() -> this.onCommandAsync(sender, command, label, args));
        return true;
    }

    public abstract void onCommandAsync(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args);
}
