package ru.ruscalworld.points.common.exceptions;

import ru.ruscalworld.points.common.core.Player;

public class NotAPlayerException extends InvalidExecutorException {
    public NotAPlayerException() {
        super(Player.class);
    }
}
