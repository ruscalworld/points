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
    public ViewPoint(String name) {
        super(name, InputType.NAME);
    }

    @Override
    public @Nullable Component execute(CommandExecutor executor) throws ActionException {
        Point point = this.getPoint();

        return Component.translatable(
                "point.view", Styles.main(),
                point.getDisplaySlug(Styles.contrast()),
                point.getLocation().getCoordinatesComponent(Styles.contrast()),
                Component.text(point.getLocation().getWorldName(), Styles.contrast())
        );
    }
}
