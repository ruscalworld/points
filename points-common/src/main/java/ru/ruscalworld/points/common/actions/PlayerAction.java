package ru.ruscalworld.points.common.actions;

import ru.ruscalworld.points.common.core.Action;
import ru.ruscalworld.points.common.core.CommandExecutor;
import ru.ruscalworld.points.common.core.Player;

public abstract class PlayerAction implements Action {
    @Override
    public final boolean canExecute(CommandExecutor executor) {
        return executor instanceof Player;
    }
}
