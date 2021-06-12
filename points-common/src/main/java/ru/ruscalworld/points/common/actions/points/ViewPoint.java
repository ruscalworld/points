package ru.ruscalworld.points.common.actions.points;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.Nullable;
import ru.ruscalworld.points.common.Points;
import ru.ruscalworld.points.common.actions.PointAction;
import ru.ruscalworld.points.common.core.CommandExecutor;
import ru.ruscalworld.points.common.exceptions.ActionException;
import ru.ruscalworld.points.common.models.Point;
import ru.ruscalworld.points.common.util.Messages;
import ru.ruscalworld.points.common.util.Permission;
import ru.ruscalworld.points.common.util.Styles;
import ru.ruscalworld.storagelib.Storage;
import ru.ruscalworld.storagelib.exceptions.NotFoundException;

public class ViewPoint extends PointAction {
    public ViewPoint(String slug) {
        super(slug);
    }

    @Override
    public @Nullable Component execute(CommandExecutor executor) throws ActionException {
        Storage storage = Points.getInstance().getStorage();
        Point point;

        try {
            point = storage.find(Point.class, "name", this.getSlug());
        } catch (NotFoundException exception) {
            throw new ActionException(Messages.pointNotFound(exception.getKeyValue().toString()));
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ActionException(Messages.unableToRetrieve());
        }

        if (point.isPrivate() && executor.isPlayer() && !point.isOwner(executor)) {
            // Check if executor has permission to view this point only if it is private
            new Permission("view.private").ensureHas(executor);
        }

        return Component.translatable(
                "point.view", Styles.main(),
                Component.text(point.getName(), Styles.contrast()),
                point.getLocation().getCoordinatesComponent(Styles.contrast()),
                Component.text(point.getLocation().getWorldName(), Styles.contrast())
        );
    }

    @Override
    public void ensureCanExecute(CommandExecutor executor) throws ActionException {
        super.ensureCanExecute(executor);
        new Permission("view").ensureHas(executor);
    }
}
