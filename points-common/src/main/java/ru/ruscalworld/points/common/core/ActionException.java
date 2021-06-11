package ru.ruscalworld.points.common.core;

import net.kyori.adventure.text.Component;

public class ActionException extends Exception {
    private final Component messageComponent;

    public ActionException(Component messageComponent) {
        this.messageComponent = messageComponent;
    }

    public Component getMessageComponent() {
        return messageComponent;
    }
}
