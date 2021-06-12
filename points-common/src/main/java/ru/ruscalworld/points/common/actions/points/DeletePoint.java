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
    public DeletePoint(String slug) {
        super(slug);
    }

    @Override
    public @Nullable Component execute(CommandExecutor executor) throws ActionException {
        Storage storage = Points.getInstance().getStorage();
        Point point;

        try {
            point = storage.find(Point.class, "slug", this.getSlug());
        } catch (NotFoundException exception) {
            throw new ActionException(Messages.pointNotFound(exception.getKeyValue().toString()));
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ActionException(Messages.unableToRetrieve());
        }


        if (executor.isPlayer() && !point.getOwnerID().equals(((Player) executor).getUUID())) {
            // Check for permission to delete others' points only if executor is not player and not owner of this point
            new Permission("delete.others").ensureHas(executor);
        }

        try {
            storage.delete(point);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ActionException(Component.translatable("errors.point.delete", Styles.main()));
        }

        return Component.translatable(
                "points.delete.success", Styles.main(),
                Component.text(point.getName(), Styles.contrast())
        );
    }
}
