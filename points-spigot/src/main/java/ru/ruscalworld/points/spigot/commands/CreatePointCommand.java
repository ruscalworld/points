package ru.ruscalworld.points.spigot.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.ruscalworld.points.common.Points;
import ru.ruscalworld.points.common.actions.points.CreatePoint;
import ru.ruscalworld.points.common.core.Action;
import ru.ruscalworld.points.common.core.CommandExecutor;
import ru.ruscalworld.points.common.util.Messages;
import ru.ruscalworld.points.spigot.impl.BukkitPlayer;

import java.util.function.BiConsumer;

public class CreatePointCommand extends AsyncCommandExecutor {
    @Override
    public void onCommandAsync(@NotNull CommandExecutor executor, @NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            executor.sendMessage(Messages.incorrectUsage(label, "<name>"));
            return;
        }

        BiConsumer<Action, CommandExecutor> actionDispatcher = Points.getInstance().getActionDispatcher();
        actionDispatcher.accept(new CreatePoint(String.join(" ", args)), new BukkitPlayer(sender));
    }
}
