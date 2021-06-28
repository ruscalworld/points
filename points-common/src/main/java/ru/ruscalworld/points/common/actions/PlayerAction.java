package ru.ruscalworld.points.common.actions;

import ru.ruscalworld.points.common.core.Action;
import ru.ruscalworld.points.common.core.CommandExecutor;
import ru.ruscalworld.points.common.core.Player;
import ru.ruscalworld.points.common.exceptions.ActionException;
import ru.ruscalworld.points.common.exceptions.NotAPlayerException;

public abstract class PlayerAction implements Action {
    @Override
    public void ensureCanExecute(CommandExecutor executor) throws ActionException {
        if (executor.isPlayer()) return;
        throw new NotAPlayerException();
    }

    public Player getPlayer(CommandExecutor executor) throws ActionException {
        if (executor instanceof Player) return ((Player) executor);
        throw new NotAPlayerException();
    }
}
