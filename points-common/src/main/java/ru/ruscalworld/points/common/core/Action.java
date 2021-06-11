package ru.ruscalworld.points.common.core;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

public interface Action {
    @Nullable Component execute(CommandExecutor executor) throws ActionException;
    boolean canExecute(CommandExecutor executor);
}
