package ru.ruscalworld.points.spigot.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.ruscalworld.points.common.Points;
import ru.ruscalworld.points.common.actions.points.ViewPoint;
import ru.ruscalworld.points.common.core.Action;
import ru.ruscalworld.points.common.core.CommandExecutor;
import ru.ruscalworld.points.spigot.impl.BukkitPlayer;

import java.util.function.BiConsumer;

public class ViewPointCommand extends AsyncCommandExecutor {
    @Override
    public void onCommandAsync(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            sender.sendMessage("Not enough arguments!");
            return;
        }

        BiConsumer<Action, CommandExecutor> actionDispatcher = Points.getInstance().getActionDispatcher();
        actionDispatcher.accept(new ViewPoint(String.join(" ", args)), new BukkitPlayer(sender));
    }
}
