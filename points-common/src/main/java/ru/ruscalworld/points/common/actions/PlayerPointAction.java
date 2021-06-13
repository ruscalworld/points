package ru.ruscalworld.points.common.actions;

import ru.ruscalworld.points.common.core.CommandExecutor;
import ru.ruscalworld.points.common.exceptions.ActionException;
import ru.ruscalworld.points.common.exceptions.NotAPlayerException;

public abstract class PlayerPointAction extends PointAction {
    private final String slug;

    protected PlayerPointAction(String slug) {
        super(slug);
        this.slug = slug;
    }

    @Override
    public void ensureCanExecute(CommandExecutor executor) throws ActionException {
        if (executor.isPlayer()) return;
        throw new NotAPlayerException();
    }

    public String getInput() {
        return slug;
    }
}
