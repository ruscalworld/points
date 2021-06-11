package ru.ruscalworld.points.common.exceptions;

import net.kyori.adventure.text.Component;

import java.util.Locale;

public class InvalidExecutorException extends ActionException {
    private final Class<?> requiredType;

    public InvalidExecutorException(Class<?> requiredType) {
        super(Component.text("You must be a " + requiredType.getSimpleName().toLowerCase(Locale.ROOT) + " to do this"));
        this.requiredType = requiredType;
    }

    public Class<?> getRequiredType() {
        return requiredType;
    }
}
