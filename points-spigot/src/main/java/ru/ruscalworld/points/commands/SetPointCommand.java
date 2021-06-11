package ru.ruscalworld.points.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.ruscalworld.points.common.Points;
import ru.ruscalworld.points.common.actions.points.CreatePoint;
import ru.ruscalworld.points.common.core.Action;
import ru.ruscalworld.points.common.core.CommandExecutor;
import ru.ruscalworld.points.common.exceptions.ActionException;
import ru.ruscalworld.points.impl.BukkitCommandExecutor;

import java.util.function.BiConsumer;

public class SetPointCommand extends AsyncCommandExecutor {
    @Override
    public void onCommandAsync(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            sender.sendMessage("Not enough arguments!");
            return;
        }

        BiConsumer<Action, CommandExecutor> actionDispatcher = Points.getInstance().getActionDispatcher();
        actionDispatcher.accept(new CreatePoint(String.join(" ", args)), new BukkitCommandExecutor(sender));
    }
}
