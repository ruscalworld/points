package ru.ruscalworld.points.spigot.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.ruscalworld.points.common.Points;
import ru.ruscalworld.points.common.actions.points.DeletePoint;
import ru.ruscalworld.points.common.core.Action;
import ru.ruscalworld.points.common.core.CommandExecutor;
import ru.ruscalworld.points.spigot.impl.BukkitCommandExecutor;

import java.util.function.BiConsumer;

public class DeletePointCommand extends AsyncCommandExecutor {
    @Override
    public void onCommandAsync(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            sender.sendMessage("Not enough arguments!");
            return;
        }

        BiConsumer<Action, CommandExecutor> actionDispatcher = Points.getInstance().getActionDispatcher();
        actionDispatcher.accept(new DeletePoint(args[0]), new BukkitCommandExecutor(sender));
    }
}
