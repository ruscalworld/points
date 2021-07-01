package ru.ruscalworld.points.common.actions.points;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;
import ru.ruscalworld.points.common.Points;
import ru.ruscalworld.points.common.actions.PlayerPointAction;
import ru.ruscalworld.points.common.actions.PointAction;
import ru.ruscalworld.points.common.core.CommandExecutor;
import ru.ruscalworld.points.common.core.Player;
import ru.ruscalworld.points.common.exceptions.ActionException;
import ru.ruscalworld.points.common.models.Point;
import ru.ruscalworld.points.common.util.Messages;
import ru.ruscalworld.points.common.util.Permission;
import ru.ruscalworld.points.common.util.Styles;
import ru.ruscalworld.storagelib.Storage;
import ru.ruscalworld.storagelib.exceptions.NotFoundException;

public class DeletePoint extends PointAction {
    public DeletePoint(String name) {
        super(name, InputType.NAME);
    }

    @Override
    public @Nullable Component execute(CommandExecutor executor) throws ActionException {
        Storage storage = Points.getInstance().getStorage();
        Point point = this.getPoint();

        if (!point.isOwner(executor)) {
            // Check for permission to delete others' points only if executor is not owner of this point
            new Permission("delete.others").ensureHas(executor);
        }

        try {
            storage.delete(point);
            point.deleteMarker();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ActionException(Component.translatable("errors.point.delete", Styles.main()));
        }

        return Component.translatable(
                "point.delete.success", Styles.main(),
                point.getDisplayName(Styles.contrast())
        );
    }
}
