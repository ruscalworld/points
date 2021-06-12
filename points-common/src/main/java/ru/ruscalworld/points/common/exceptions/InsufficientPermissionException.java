package ru.ruscalworld.points.common.exceptions;

import net.kyori.adventure.text.Component;

public class InsufficientPermissionException extends ActionException {
    private final String permission;

    public InsufficientPermissionException(String permission) {
        super(Component.translatable("errors.permissions.default"));
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
