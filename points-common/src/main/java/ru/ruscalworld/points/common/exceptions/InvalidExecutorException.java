package ru.ruscalworld.points.common.exceptions;

import net.kyori.adventure.text.Component;
import ru.ruscalworld.points.common.util.Styles;

import java.util.Locale;

public class InvalidExecutorException extends ActionException {
    private final Class<?> requiredType;

    public InvalidExecutorException(Class<?> requiredType) {
        super(Component.translatable(
                "errors.actions.executor", Styles.main(),
                Component.text(requiredType.getSimpleName().toLowerCase(Locale.ROOT), Styles.main())
        ));

        this.requiredType = requiredType;
    }

    public Class<?> getRequiredType() {
        return requiredType;
    }
}
