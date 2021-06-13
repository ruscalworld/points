package ru.ruscalworld.points.spigot.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.ruscalworld.points.common.Points;
import ru.ruscalworld.points.common.actions.points.ChangePointPrivacy;
import ru.ruscalworld.points.common.actions.points.ChangePointVisibility;
import ru.ruscalworld.points.common.core.Action;
import ru.ruscalworld.points.common.core.CommandExecutor;
import ru.ruscalworld.points.common.util.Messages;

import java.util.Locale;
import java.util.function.BiConsumer;

public class PointCommand extends AsyncCommandExecutor {
    @Override
    public void onCommandAsync(@NotNull CommandExecutor executor, @NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            usageError(label, "<args...>", executor);
            return;
        }

        BiConsumer<Action, CommandExecutor> actionDispatcher = Points.getInstance().getActionDispatcher();
        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "hide":
                if (args.length < 2) usageError(label, "hide <point>", executor);
                else actionDispatcher.accept(new ChangePointVisibility(args[1], true), executor);
                break;
            case "show":
                if (args.length < 2) usageError(label, "show <point>", executor);
                else actionDispatcher.accept(new ChangePointVisibility(args[1], false), executor);
                break;
            case "set":
                if (args.length < 3) {
                    usageError(label, "set <point> <args...>", executor);
                    return;
                }

                switch (args[2].toLowerCase(Locale.ROOT)) {
                    case "hidden":
                        if (args.length < 4) usageError(label, "set <point> hidden <true/false>", executor);
                        else actionDispatcher.accept(new ChangePointVisibility(args[1], args[3].equals("true")), executor);
                        break;
                    case "private":
                        if (args.length < 4) usageError(label, "set <point> private <true/false>", executor);
                        else actionDispatcher.accept(new ChangePointPrivacy(args[1], args[3].equals("true")), executor);
                        break;
                    default:
                        usageError(label, "set <point> <args...>", executor);
                        break;
                }

                break;
            default:
                usageError(label, "<args...>", executor);
                break;
        }
    }

    public void usageError(String label, String args, CommandExecutor executor) {
        executor.sendMessage(Messages.incorrectUsage(label, args));
    }
}
