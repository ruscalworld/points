package ru.ruscalworld.points.common.actions;

import ru.ruscalworld.points.common.core.Action;
import ru.ruscalworld.points.common.core.CommandExecutor;

public abstract class PointAction implements Action {
    private final String slug;

    protected PointAction(String slug) {
        this.slug = slug;
    }

    @Override
    public void ensureCanExecute(CommandExecutor executor) {

    }

    public String getSlug() {
        return slug;
    }
}