package ru.ruscalworld.points.common.util;

import ru.ruscalworld.points.common.core.CommandExecutor;
import ru.ruscalworld.points.common.exceptions.InsufficientPermissionException;

public class Permission {
    private final String permission;

    public Permission(String permission) {
        this.permission = getNamespacedPermission(permission);
    }

    public void ensureHas(CommandExecutor executor) throws InsufficientPermissionException {
        if (executor.hasPermission(this.getPermission())) return;
        throw new InsufficientPermissionException(this.getPermission());
    }

    public static String getNamespacedPermission(String permission) {
        return "points." + permission;
    }

    public String getPermission() {
        return permission;
    }
}
