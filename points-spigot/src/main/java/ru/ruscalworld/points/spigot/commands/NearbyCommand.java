package ru.ruscalworld.points.spigot.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.ruscalworld.points.common.Points;
import ru.ruscalworld.points.common.actions.points.ListNearbyPoints;
import ru.ruscalworld.points.common.core.Action;
import ru.ruscalworld.points.common.core.CommandExecutor;

import java.util.function.BiConsumer;

public class NearbyCommand extends AsyncCommandExecutor {
    @Override
    public void onCommandAsync(@NotNull CommandExecutor executor, @NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        BiConsumer<Action, CommandExecutor> actionDispatcher = Points.getInstance().getActionDispatcher();
        actionDispatcher.accept(new ListNearbyPoints(), executor);
    }
}
