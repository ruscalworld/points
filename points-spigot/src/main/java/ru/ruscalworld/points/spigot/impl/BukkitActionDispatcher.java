package ru.ruscalworld.points.spigot.impl;

import ru.ruscalworld.points.common.core.Action;
import ru.ruscalworld.points.common.core.CommandExecutor;
import ru.ruscalworld.points.common.exceptions.ActionException;

import java.util.function.BiConsumer;

public class BukkitActionDispatcher implements BiConsumer<Action, CommandExecutor> {
    @Override
    public void accept(Action action, CommandExecutor executor) {
        try {
            action.ensureCanExecute(executor);
            executor.sendMessage(action.execute(executor));
        } catch (ActionException exception) {
            executor.sendMessage(exception.getMessageComponent());
        }
    }
}
