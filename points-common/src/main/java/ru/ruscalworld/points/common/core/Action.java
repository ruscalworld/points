package ru.ruscalworld.points.common.core;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;
import ru.ruscalworld.points.common.exceptions.ActionException;

public interface Action {
    @Nullable Component execute(CommandExecutor executor) throws ActionException;
    void ensureCanExecute(CommandExecutor executor) throws ActionException;
}
