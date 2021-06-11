package ru.ruscalworld.points.common.actions;

public abstract class PlayerPointAction extends PlayerAction {
    private final String slug;

    protected PlayerPointAction(String slug) {
        this.slug = slug;
    }

    public String getSlug() {
        return slug;
    }
}
