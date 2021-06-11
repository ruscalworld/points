package ru.ruscalworld.points.common.actions;

import ru.ruscalworld.points.common.core.Action;
import ru.ruscalworld.points.common.core.CommandExecutor;
import ru.ruscalworld.points.common.core.Player;
import ru.ruscalworld.points.common.exceptions.NotAPlayerException;

public abstract class PlayerAction implements Action {
    @Override
    public final void ensureCanExecute(CommandExecutor executor) throws NotAPlayerException {
        if (executor instanceof Player) return;
        throw new NotAPlayerException();
    }
}
